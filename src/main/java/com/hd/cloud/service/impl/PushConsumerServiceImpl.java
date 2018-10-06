package com.hd.cloud.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hd.cloud.MomentServiceApplication.GetuiConfigInfo;
import com.hd.cloud.bo.PushRecord;
import com.hd.cloud.bo.UserPush;
import com.hd.cloud.dao.PushRecordDao;
import com.hd.cloud.dao.UserPushDao;
import com.hd.cloud.model.ListPushModel;
import com.hd.cloud.model.SimplePushModel;
import com.hd.cloud.push.PushModeFactory;
import com.hd.cloud.push.PushModelCont;
import com.hd.cloud.service.GetuiConfigInfoService;
import com.hd.cloud.service.PushConsumerService;
import com.hd.cloud.util.ConstantUtil;
import com.hd.cloud.util.ErrorCode;
import com.hd.cloud.util.PushToSingle;
import com.hlb.cloud.util.CommonConstantUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: PushConsumerServiceImpl
 * @Description: 推送rabbitmq实现类
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2017年11月21日 上午11:13:05
 *
 */
@Slf4j
@Service
public class PushConsumerServiceImpl implements PushConsumerService {

	@Inject
	private PushRecordDao pushRecordDao;

	@Inject
	private GetuiConfigInfoService getuiConfigInofService;

	@Inject
	private UserPushDao userPushDao;

	@Override
	public String simplePush(SimplePushModel simplePushModel) {
		log.info("into simplePush {} ", simplePushModel);
		UserPush userPush = userPushDao.findByUserId(simplePushModel.getToUserId(), simplePushModel.getToAppType());
		if (userPush == null || StringUtils.isBlank(userPush.getCid()) || userPush.getCid().equals("0")) {
			log.info("Cid  is not exist");
			return null;
		}
		log.info("##############userPush:{}", userPush);
		PushRecord record = PushRecord.builder().fromUserId(simplePushModel.getFromUserId())
				.fromCompanyId(simplePushModel.getFromCompanyId())// 商家推送扩展字段
				.fromAppType(0).toUserId(simplePushModel.getToUserId()).toCompanyId(0)// 商家推送扩展字段
				.toAppType(simplePushModel.getToAppType()).toCid(userPush.getCid()).deviceType(userPush.getDeviceType())
				.contentType(1).messageType(3).content(simplePushModel.getContent() + simplePushModel.getJson())
				.status(0).createTime(new Timestamp(System.currentTimeMillis()))
				.createBy((int) simplePushModel.getFromUserId()).updateTime(new Timestamp(System.currentTimeMillis()))
				.updateBy((int) simplePushModel.getFromUserId()).activeFlag(CommonConstantUtil.ACTIVE_FLAG_Y).build();
		log.info("push record {}", record);
		pushRecordDao.addPushInfo(record);

		ITemplate tmpls = null;
		GetuiConfigInfo getuiConfig = null;
		log.info("透穿信息：" + simplePushModel.getJson());

		if (userPush.getDeviceType() == PushToSingle.ANDROID_TYPE) {
			log.info("into android getui");
			getuiConfig = getuiConfigInofService.getGetuiConfigInfo(userPush.getAppType(), userPush.getDeviceType());

			TransmissionTemplate tmpl = (TransmissionTemplate) PushToSingle.chooseMsgTmp(PushToSingle.CUSTOM_MSG_TMP);
			tmpl.setAppId(getuiConfig.getAppId());
			tmpl.setAppkey(getuiConfig.getAppKey());
			tmpl.setTransmissionContent(simplePushModel.getJson());// 请输入需要透传的内容
			tmpl.setTransmissionType(2);// 2则广播等待客户端自启动

			tmpls = tmpl;
			log.info("getuiConfig:{}", getuiConfig);
		} else if (userPush.getDeviceType() == PushToSingle.IOS_ENTERPRISE_TYPE) {
			log.info("int IOS getui");
			getuiConfig = getuiConfigInofService.getGetuiConfigInfo(userPush.getAppType(), userPush.getDeviceType());

			log.info("jsonContent {}", simplePushModel.getJson());
			log.info("content {}", simplePushModel.getContent());
			TransmissionTemplate tmpl = (TransmissionTemplate) PushToSingle.chooseMsgTmp(PushToSingle.CUSTOM_MSG_TMP);

			String content = simplePushModel.getContent();
			try {
				tmpl.setAppId(getuiConfig.getAppId());
				tmpl.setAppkey(getuiConfig.getAppKey());
				tmpl.setTransmissionContent(simplePushModel.getJson());// 请输入需要透传的内容
				tmpl.setTransmissionType(2);// ，2则广播等待客户端自启动
				// tmpl.setDuration(begin, end); //动画的持续时间
				log.info("contentCN = {},jsonContent = {}", content, simplePushModel.getJson());

				APNPayload payload = new APNPayload();
				payload.setBadge(1);
				// payload.setContentAvailable(1);
				payload.setSound("default");
				// payload.setCategory("{'msg':'hello'}");

				APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
				alertMsg.setBody(content);
				alertMsg.setTitle(ConstantUtil.PushTitle.ASCS);
				payload.setAlertMsg(alertMsg);

				tmpl.setAPNInfo(payload);

				tmpls = tmpl;
			} catch (Exception e) {
				e.printStackTrace();
				return ErrorCode.PUSH_IOS_TMPL_EXCEPTION;
			}
		}
		log.info("push temple massage =" + tmpls == null ? null : tmpls.toString());
		try {
			List<String> cidList = new ArrayList<String>();
			cidList.add(userPush.getCid());
			PushModeFactory.getPushInstance(PushModelCont.SIMPLE_PUSH.getValue()).push(cidList, tmpls, getuiConfig);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("###########msg:{}", e.getMessage());
			return ErrorCode.PUSH_EXCEPTION;
		}

		pushRecordDao.setHasPushedById(record.getId());
		log.info("push success");
		return CommonConstantUtil.SUCCESS;

	}

	@Override
	public String listPush(ListPushModel listPushModel) {
		log.info("into listPush {} ", listPushModel);
		try {

			List<String> cidAndroidList = userPushDao.getCidList(listPushModel.getToUserIdLIst(),
					listPushModel.getToAppType(), ConstantUtil.DeviceType.ANDROID);
			List<String> cidIOSList = userPushDao.getCidList(listPushModel.getToUserIdLIst(),
					listPushModel.getToAppType(), ConstantUtil.DeviceType.IOS_ETERPRISE);
			List<String> cidIOSAppstoreList = userPushDao.getCidList(listPushModel.getToUserIdLIst(),
					listPushModel.getToAppType(), ConstantUtil.DeviceType.IOS_APPSTORE);

			GetuiConfigInfo getuiConfig = null;
			log.debug("穿透信息：" + listPushModel.getJson());

			// android
			log.info("into android getui");
			if (cidAndroidList != null && cidAndroidList.size() != 0) {
				getuiConfig = getuiConfigInofService.getGetuiConfigInfo(listPushModel.getToAppType(),
						ConstantUtil.DeviceType.ANDROID);

				TransmissionTemplate tmplAndroid = (TransmissionTemplate) PushToSingle
						.chooseMsgTmp(PushToSingle.CUSTOM_MSG_TMP);
				tmplAndroid.setAppId(getuiConfig.getAppId());
				tmplAndroid.setAppkey(getuiConfig.getAppKey());
				tmplAndroid.setTransmissionContent(listPushModel.getJson());// 请输入需要透传的内容
				tmplAndroid.setTransmissionType(2);// 2则广播等待客户端自启动

				// android PUSH
				try {
					PushModeFactory.getPushInstance(PushModelCont.PUSH_TO_LIST.getValue()).push(cidAndroidList,
							tmplAndroid, getuiConfig);
				} catch (Exception e) {
					log.info(" 1 PushModeFactory.getPushInstance {}", e);
					e.printStackTrace();
					return ErrorCode.PUSH_EXCEPTION;
				}
			}

			// IOS 企业版
			if (cidIOSList != null && cidIOSList.size() != 0) {
				log.info("int IOS getui");

				getuiConfig = getuiConfigInofService.getGetuiConfigInfo(listPushModel.getToAppType(),
						ConstantUtil.DeviceType.IOS_ETERPRISE);

				log.info("jsonContent {}", listPushModel.getJson());
				log.info("content {}", listPushModel.getContent());
				TransmissionTemplate tmplIOS = (TransmissionTemplate) PushToSingle
						.chooseMsgTmp(PushToSingle.CUSTOM_MSG_TMP);

				String content = listPushModel.getContent();
				try {
					tmplIOS.setAppId(getuiConfig.getAppId());
					tmplIOS.setAppkey(getuiConfig.getAppKey());
					tmplIOS.setTransmissionContent(listPushModel.getJson());// 请输入需要透传的内容
					tmplIOS.setTransmissionType(2);// ，2则广播等待客户端自启动
					// tmpl.setDuration(begin, end); //动画的持续时间
					log.info("contentCN = {},jsonContent = {}", content, listPushModel.getJson());
					tmplIOS.setPushInfo("actionLocKey", 1, content, "default", listPushModel.getJson(), "", "", null);
					// template.setPushInfo("actionLocKey", 4, "message", "sound",
					// "payload", "locKey", "locArgs", "launchImage","ContentAvailable");
				} catch (Exception e) {
					e.printStackTrace();
					return ErrorCode.PUSH_IOS_TMPL_EXCEPTION;
				}
				// IOS PUSH
				try {
					PushModeFactory.getPushInstance(PushModelCont.SIMPLE_PUSH.getValue()).push(cidIOSList, tmplIOS,
							getuiConfig);
				} catch (Exception e) {
					log.info(" 2 PushModeFactory.getPushInstance {}", e);
					e.printStackTrace();
					return ErrorCode.PUSH_EXCEPTION;
				}
			}

			// IOS appstore版
			if (cidIOSAppstoreList != null && cidIOSAppstoreList.size() != 0) {
				log.info("int IOS getui");

				getuiConfig = getuiConfigInofService.getGetuiConfigInfo(listPushModel.getToAppType(),
						ConstantUtil.DeviceType.IOS_APPSTORE);

				log.info("jsonContent {}", listPushModel.getJson());
				log.info("content {}", listPushModel.getContent());
				TransmissionTemplate tmplIOS = (TransmissionTemplate) PushToSingle
						.chooseMsgTmp(PushToSingle.CUSTOM_MSG_TMP);

				String content = listPushModel.getContent();
				try {
					tmplIOS.setAppId(getuiConfig.getAppId());
					tmplIOS.setAppkey(getuiConfig.getAppKey());
					tmplIOS.setTransmissionContent(listPushModel.getJson());// 请输入需要透传的内容
					tmplIOS.setTransmissionType(2);// ，2则广播等待客户端自启动
					// tmpl.setDuration(begin, end); //动画的持续时间
					log.info("contentCN = {},jsonContent = {}", content, listPushModel.getJson());
					tmplIOS.setPushInfo("actionLocKey", 1, content, "default", listPushModel.getJson(), "", "", null);
					// template.setPushInfo("actionLocKey", 4, "message", "sound",
					// "payload", "locKey", "locArgs", "launchImage","ContentAvailable");
				} catch (Exception e) {
					e.printStackTrace();
					return ErrorCode.PUSH_IOS_TMPL_EXCEPTION;
				}
				// IOS PUSH
				try {
					PushModeFactory.getPushInstance(PushModelCont.SIMPLE_PUSH.getValue()).push(cidIOSAppstoreList,
							tmplIOS, getuiConfig);
				} catch (Exception e) {
					log.info(" 3 PushModeFactory.getPushInstance {}", e);
					e.printStackTrace();
					return ErrorCode.PUSH_EXCEPTION;
				}
			}

		} catch (Exception e) {
			log.info(" ************************** PushModeFactory.getPushInstance.Exception {}", e);
			e.printStackTrace();
			return ErrorCode.PUSH_EXCEPTION;
		}
		log.info("push success");
		return CommonConstantUtil.SUCCESS;
	}

}