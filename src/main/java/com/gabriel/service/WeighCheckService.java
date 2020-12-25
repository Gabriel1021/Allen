//package com.kingdee.eas.custom.baseapp.app.implementation;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.URLEncoder;
//import java.sql.SQLException;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.Context;
//import com.kingdee.bos.dao.IObjectPK;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.ui.face.UIRuleUtil;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.eas.base.attachment.AttachmentFacadeFactory;
//import com.kingdee.eas.base.attachment.AttachmentFactory;
//import com.kingdee.eas.base.attachment.AttachmentInfo;
//import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
//import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
//import com.kingdee.eas.base.attachment.util.Resrcs;
//import com.kingdee.eas.base.attachment.util.StringUtil4File;
//import com.kingdee.eas.base.permission.UserFactory;
//import com.kingdee.eas.base.permission.UserInfo;
//import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
//import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
//import com.kingdee.eas.basedata.org.CtrlUnitInfo;
//import com.kingdee.eas.basedata.person.PersonFactory;
//import com.kingdee.eas.basedata.person.PersonInfo;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.custom.BillStatesEnum;
//import com.kingdee.eas.custom.SaleBillCreateType;
//import com.kingdee.eas.custom.baseapp.bybHandler.BybUtils;
//import com.kingdee.eas.custom.basefile.CarDataFactory;
//import com.kingdee.eas.custom.basefile.CarDataInfo;
//import com.kingdee.eas.custom.basefile.ManageOrgFactory;
//import com.kingdee.eas.custom.basefile.ManageOrgInfo;
//import com.kingdee.eas.custom.basefile.ManageOrgPositionFactory;
//import com.kingdee.eas.custom.basefile.ManageOrgPositionInfo;
//import com.kingdee.eas.custom.basefile.ManageOrgPositionPositionUserCollection;
//import com.kingdee.eas.custom.basefile.ManageOrgPositionPositionUserFactory;
//import com.kingdee.eas.custom.basefile.ManageOrgPositionPositionUserInfo;
//import com.kingdee.eas.custom.basefile.PigSaleVarietiesFactory;
//import com.kingdee.eas.custom.basefile.PigSaleVarietiesInfo;
//import com.kingdee.eas.custom.basefile.ThingTypeCollection;
//import com.kingdee.eas.custom.basefile.ThingTypeFactory;
//import com.kingdee.eas.custom.basefile.YZ_RH_CUSTFactory;
//import com.kingdee.eas.custom.basefile.YZ_RH_CUSTInfo;
//import com.kingdee.eas.custom.eartag.EarTagPoolFactory;
//import com.kingdee.eas.custom.eartag.EarTagPoolInfo;
//import com.kingdee.eas.custom.eartag.EarTagQrCodeStoreFactory;
//import com.kingdee.eas.custom.eartag.EarTagQrCodeStoreInfo;
//import com.kingdee.eas.custom.eartag.EarTagStatusEnum;
//import com.kingdee.eas.custom.eartag.EarTagStoreFactory;
//import com.kingdee.eas.custom.eartag.EarTagStoreInfo;
//import com.kingdee.eas.custom.eartag.app.EarTagUtils;
//import com.kingdee.eas.custom.fi.VerifyCapitalEnum;
//import com.kingdee.eas.custom.producebusiness.IPoundCheckZJEntry;
//import com.kingdee.eas.custom.producebusiness.ISaleBill;
//import com.kingdee.eas.custom.producebusiness.PigInventoryEarTagEntryInfo;
//import com.kingdee.eas.custom.producebusiness.PoundCheckEarTagEntryCollection;
//import com.kingdee.eas.custom.producebusiness.PoundCheckEarTagEntryFactory;
//import com.kingdee.eas.custom.producebusiness.PoundCheckEarTagEntryInfo;
//import com.kingdee.eas.custom.producebusiness.PoundCheckEntryInfo;
//import com.kingdee.eas.custom.producebusiness.PoundCheckFactory;
//import com.kingdee.eas.custom.producebusiness.PoundCheckInfo;
////import com.kingdee.eas.custom.producebusiness.PoundCheckWeihtListCollection;
////import com.kingdee.eas.custom.producebusiness.PoundCheckWeihtListFactory;
////import com.kingdee.eas.custom.producebusiness.PoundCheckWeihtListInfo;
//import com.kingdee.eas.custom.producebusiness.PoundCheckZJEntryCollection;
//import com.kingdee.eas.custom.producebusiness.PoundCheckZJEntryFactory;
//import com.kingdee.eas.custom.producebusiness.PoundCheckZJEntryInfo;
//import com.kingdee.eas.custom.producebusiness.SaleBillAddSubstractEntryInfo;
//import com.kingdee.eas.custom.producebusiness.SaleBillCollection;
//import com.kingdee.eas.custom.producebusiness.SaleBillEarTagEntryInfo;
//import com.kingdee.eas.custom.producebusiness.SaleBillEntryInfo;
//import com.kingdee.eas.custom.producebusiness.SaleBillInfo;
//import com.kingdee.eas.custom.producebusiness.Yz_rh_batchFactory;
//import com.kingdee.eas.custom.producebusiness.Yz_rh_batchInfo;
//import com.kingdee.eas.custom.producebusiness.Yz_rh_deathEarTagEntryCollection;
//import com.kingdee.eas.custom.producebusiness.Yz_rh_deathEarTagEntryFactory;
//import com.kingdee.eas.framework.CoreBaseCollection;
//import com.kingdee.eas.framework.Result;
//import com.kingdee.eas.send.EnterpriseWXMsgUtil;
//import com.kingdee.eas.util.app.DbUtil;
//import com.kingdee.eas.zbsd.baseframework.LoadometerType;
//import com.kingdee.eas.zbsd.baseframework.ToolBoxFacadeFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.AddSubtractTypeFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.AddSubtractTypeInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.IAddSubtractType;
//import com.kingdee.eas.zbsd.zbsdbasedata.IQuotePriceArea;
//import com.kingdee.eas.zbsd.zbsdbasedata.IQuotePriceAreaServiceOrg;
//import com.kingdee.eas.zbsd.zbsdbasedata.IQuotePriceAreaSubCompany;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaCollection;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaServiceOrgFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaServiceOrgInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaSubCompanyFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.QuotePriceAreaSubCompanyInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.ISaleShipmentDetail;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleBillFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleCustomerFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleCustomerInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleShipmentDetailFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleShipmentDetailInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleShipmentFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleShipmentInfo;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.NumericExceptionSubItem;
//
///***
// * 销售监磅 2018-06-14
// */
//public class WeighCheckService
//{
//
//	private static Logger logger = Logger
//			.getLogger("com.kingdee.eas.custom.baseapp.app.implementation.WeighCheckService");
//
//	/**
//	 * 销售监磅 保存
//	 * @throws Exception
//	 */
//	public static String saveWeighCheck(Context ctx, JSONObject json, ArrayList<File> tempFiles,
//			CoreBaseCollection attachmentCols) throws Exception{
//		JSONObject resultJson = new JSONObject();
//		JSONObject jsons = json.getJSONObject("data");
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "销售监磅新增成功");
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String batchid = jsons.getString("batchid");
//		String userName = jsons.getString("userName");
//		String carId = jsons.getString("carData");
//		int submitType = jsons.getIntValue("submitType");
//		String shipmentID = jsons.getString("shipmentID");
//		String varietyId = jsons.getString("varietyId");
//		String customID = jsons.getString("customID");
//		int Iscp = jsons.getIntValue("cp");
//		//新增车辆信息字段
//		String fscale = jsons.getString("fscale");//油表刻度
//		int ftankqty = UIRuleUtil.isNotNull(jsons.getString("ftankqty"))?jsons.getIntValue("ftankqty"):0;//油箱个数
//		int fsparetireqty = UIRuleUtil.isNotNull(jsons.getString("fsparetireqty"))?jsons.getIntValue("fsparetireqty"):0;//备胎个数
//		int fcanvasqty = UIRuleUtil.isNotNull(jsons.getString("fcanvasqty"))?jsons.getIntValue("fcanvasqty"):0;//帆布块数
//		Boolean fisshadecloth = jsons.getBoolean("fisshadecloth");//有无遮阳布
//		//新增车辆信息字段	结束
//		PigSaleVarietiesInfo varietyInfo = null;
//		Date date1 = null,date2 = null;
//		IObjectPK pk2;
//		CoreBaseCollection attachmentColslits = new CoreBaseCollection();
//		com.kingdee.eas.base.permission.UserInfo uInfo = null;
//
//		uInfo = UserFactory.getLocalInstance(ctx)
//				.getUserCollection("where number='" + userName + "'").get(0);
//		if(UIRuleUtil.isNull(uInfo)){
//			resultJson.put("code", "1105");
//			resultJson.put("msg", "未找到人员!");
//			return resultJson.toString();
//		}
//		if (varietyId != null)
//		{
//			varietyInfo = PigSaleVarietiesFactory.getLocalInstance(ctx)
//					.getPigSaleVarietiesInfo(new ObjectUuidPK(varietyId));
//
//		}
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("*"));
//		sic.add(new SelectorItemInfo("StorageOrgUnit.*"));
//		sic.add(new SelectorItemInfo("ServiceDepart.*"));
//		sic.add(new SelectorItemInfo("ServiceDepart.TheCompany.*"));
//
//
//		Yz_rh_batchInfo batchInfo = Yz_rh_batchFactory.getLocalInstance(ctx)
//				.getYz_rh_batchInfo(new ObjectUuidPK(batchid), sic);
//
//		PoundCheckInfo info;
//		SaleShipmentInfo shipmentInfo = new SaleShipmentInfo();
//		SaleShipmentDetailInfo saleshipInfo = new SaleShipmentDetailInfo();
//		if (jsons.containsKey("rcordid") && jsons.getString("rcordid") != null)
//		{
//			String id = jsons.getString("rcordid");
//			info = PoundCheckFactory.getLocalInstance(ctx).getPoundCheckInfo(
//					new ObjectUuidPK(id));
//			info.getZJEntry().clear();
//		}
//		else
//		{
//			info = new PoundCheckInfo();
//			info.setCreateTime(new Timestamp(new Date().getTime()));
//			info.setCreator(uInfo);
//			info.setBizDate(new Date());
//			info.setId(BOSUuid.create(info.getBOSType()));
//			if (Util.isCodeRuleEnable(ctx, info))
//			{
//				String number = Util.getAutoCode(ctx, info);
//				info.setNumber(number);
//			}
//		}
//		//车辆信息
//		info.setScale(fscale);
//		info.setTankQty(ftankqty);
//		info.setSpareTireQty(fsparetireqty);
//		info.setCanvasQty(fcanvasqty);
//		info.setIsShadeCloth(fisshadecloth);
//		//车辆信息	结束
//
////		info.setDeviceId(UIRuleUtil.getString(jsons.get("customID"))); //电子秤ID
////		info.setDeviceNumber(UIRuleUtil.getString(jsons.get("deviceNumber"))); //电子秤编码
////		info.setDeviceName(UIRuleUtil.getString(jsons.get("deviceAlias"))); //电子秤名称
//
//		//系统估算偏差
//		info.setSystemSub(jsons.getBigDecimal("systemSub"));
//		//上市估重偏差
//		info.setOnlineSub(jsons.getBigDecimal("onlineSub"));
//
//		info.setBillStatus(BillStatesEnum.save);
//		info.setSourceBillId(shipmentID);
//		if(shipmentID !=null)
//		{
//			saleshipInfo = SaleShipmentDetailFactory.getLocalInstance(ctx)
//					.getSaleShipmentDetailInfo(new ObjectUuidPK(shipmentID));
//
//			shipmentInfo = SaleShipmentFactory.getLocalInstance(ctx).getSaleShipmentInfo(
//					new ObjectUuidPK(saleshipInfo.getParent().getId()));
//
//			com.kingdee.eas.zbsd.zbsdbizbill.SaleBillInfo sbInfo = SaleBillFactory.getLocalInstance(ctx).getSaleBillInfo(
//					new ObjectUuidPK(shipmentInfo.getSourceBillId().toString()));
//			if(sbInfo.getVerifyCapital()!=null && sbInfo.getVerifyCapital().equals(VerifyCapitalEnum.finish))
//			{
//				resultJson.put("code", "1105");
//				resultJson.put("msg","此张销售通知单的上游销售订单已完成销售,不能提交");
//				return resultJson.toString();
//			}
//		}
//		if((shipmentID != null)&&(Iscp==0))
//		{
//			String infoid = info.getId().toString();
//			StringBuffer sql = new StringBuffer();
//			sql.append(" /*dialect*/ ");
//			sql.append("select b.fid,\n" +
//					"       nvl(jb.xsqty,0) as xsqty,\n" +
//					"       round(b.FSHIPPINGQTY*1.2,0) as shipqty\n" +
//					"from T_ZB_SD_SaleShipment a\n" +
//					"inner join T_ZB_SD_SALESHIPMENTDETAIL b on b.FPARENTID = a.fid\n" +
//					"inner join CT_BAS_PigSaleVarieties c on c.fid = b.FSaleProductID\n" +
//					"left join\n" +
//					"(\n" +
//					"     select a.FSOURCEBILLID,\n" +
//					"            sum(a.CFQTY) as xsqty\n" +
//					"      from CT_PRO_PoundCheck a\n" +
//					"     where a.FBILLSTATUS <> 90\n" +
//					"       and a.fid <> '"+infoid+"'\n" +
//					"     group by a.FSOURCEBILLID\n" +
//					")jb on jb.fsourcebillid =b.fid\n" +
//					"where a.FBILLSTATUS = 10\n" +
//					"  and b.fid = '"+shipmentID+"'");
//			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//
//			if(rs!=null && rs.size()>0)
//			{
//				int a=0,b=0,c=0;
//				rs.next();
//				a = rs.getInt("shipqty");
//				b = rs.getInt("xsqty");
//				if(!jsons.getString("qty").equals("")&&jsons.getString("qty")!=null)
//				{
//					c = jsons.getIntValue("qty");
//				}
//				if((b+c)>a)
//				{
//					resultJson.put("code", "1105");
//					resultJson.put("msg","邦养宝：已执行的监磅单数量之和:"+(b+c)+"超过了通知单数量的1.2倍:"+a+
//							"\n 1、点右上角记录，找到已暂存监磅单续传" +
//							"\n 2、核实是否选错了通知单做监磅");
//					return resultJson.toString();
//				}
//			}
//		}
//		info.setDescription(customID);
//		if (jsons.getString("pigStartTime") != null)
//		{
//			date1 = format.parse(jsons.getString("pigStartTime"));
//			info.setPigStartTime(date1);
//		}
//		if (jsons.getString("pigEndTime") != null)
//		{
//			date2 = format.parse(jsons.getString("pigEndTime"));
//			info.setPigEndTime(date2);
//		}
//		info.setBthno(batchInfo);
//		info.setServiceDept(batchInfo.getServiceDepart());
//		info.setStorageOrgUnit(batchInfo.getStorageOrgUnit());
//		info.setCompanyOrgUnit(batchInfo.getServiceDepart().getTheCompany());
//		info.setVariety(varietyInfo);
//		if (carId != null && carId.length() > 0)
//		{
//			CarDataInfo carData = CarDataFactory.getLocalInstance(ctx).getCarDataInfo(
//					new ObjectUuidPK(carId));
//			info.setCarData(carData);
//			if(carData.getBIMUDF0001()!=null)
//			{
//				info.setSaveTare(carData.getBIMUDF0001());
//			}
//		}
//		if (jsons.getString("tare") != null && jsons.getString("tare").length() > 0)
//		{
//			info.setTare(jsons.getBigDecimal("tare"));
//		}
//		if (jsons.getString("grossWeight") != null
//				&& jsons.getString("grossWeight").length() > 0)
//		{
//			info.setGrossWeight(jsons.getBigDecimal("grossWeight"));// 毛重
//		}
//		BigDecimal netWeight = new BigDecimal(0);
//		if (jsons.getString("netWeight") != null)
//		{
//			netWeight = jsons.getBigDecimal("netWeight");
//			info.setNetWeight(netWeight);// 净重
//		}
//		BigDecimal avgWeight = new BigDecimal(0);
//		if(jsons.getString("qty")!=null &&(!jsons.getString("qty").equals("0")))
//		{
//			avgWeight =netWeight.divide(jsons.getBigDecimal("qty"),RoundingMode.HALF_UP);
//			info.setAvgWeight(avgWeight);// 均重
//		}
//		else
//		{
//			info.setAvgWeight(avgWeight);
//		}
//		if(!jsons.getString("qty").equals("")&&jsons.getString("qty")!=null)
//		{
//			info.setQty(jsons.getIntValue("qty"));// 猪只头数
//		}
//		JSONArray zxjmlist = jsons.getJSONArray("zjxmList");
//		BigDecimal subamount,Newamount = BigDecimal.ZERO;
//		if(zxjmlist!=null && zxjmlist.size()>0)
//		{
//			AddSubtractTypeInfo addInfo;
//			IAddSubtractType IAddSubType = AddSubtractTypeFactory.getLocalInstance(ctx);
//			for(int i = 0 ; i<zxjmlist.size();i++ )
//			{
//				PoundCheckZJEntryInfo pcInfo = new PoundCheckZJEntryInfo();
//				String zxid = zxjmlist.getJSONObject(i).getString("zjxmid");
//				addInfo = IAddSubType.getAddSubtractTypeInfo(new ObjectUuidPK(zxid));
//				subamount =  zxjmlist.getJSONObject(i).getBigDecimal("money");
//
//				pcInfo.setId(BOSUuid.create(pcInfo.getBOSType()));
//				pcInfo.setParent(info);
//				pcInfo.setMoney(subamount);
//				pcInfo.setZjxm(addInfo);
//				pcInfo.setYsfs(addInfo.getYsfs());
//
//				if(addInfo.getYsfs().getValue()==0)
//				{
//					Newamount=Newamount.add(subamount);
//				}
//				else
//				{
//					Newamount=Newamount.subtract(subamount);
//				}
//				info.getZJEntry().add(pcInfo);
//			}
//		}
//		else
//		{
//			PoundCheckZJEntryInfo addsubInfo ;
//			PoundCheckZJEntryCollection AddSubCol;
//			AddSubCol = info.getZJEntry();
//			if(AddSubCol.size()>=0)
//			{
//				for(int j=0 ;j<AddSubCol.size();j++)
//				{
//					addsubInfo = AddSubCol.get(j);
//					if(addsubInfo.getYsfs().getValue()==0)
//					{
//						Newamount=Newamount.add(addsubInfo.getMoney());
//					}
//					else
//					{
//						Newamount = Newamount.subtract(addsubInfo.getMoney());
//					}
//				}
//			}
//		}
//		info.setActualMoney(Newamount);
//		if (jsons.getString("price") != null)
//		{
//			BigDecimal price = jsons.getBigDecimal("price");
//			info.setPrice(price);
//			info.setAmount(price.multiply(netWeight).multiply(new BigDecimal(2)).add(Newamount)); // 设置金额;
//
//		}
//		info.setWeighbridge(LoadometerType.getEnum(jsons.getString("loadometerType"))); // 地磅类型
//
//
//
//		//耳标
//		JSONArray earNumbers=jsons.getJSONArray("earNumbers");
//
//		if(earNumbers!=null){
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			for(Object o:earNumbers){
//				JSONObject earNumber=(JSONObject)o;
//				PoundCheckEarTagEntryInfo eteInfo=new PoundCheckEarTagEntryInfo();
//				EarTagStoreInfo ear=EarTagStoreFactory.getLocalInstance(ctx).getEarTagStoreCollection("where earNumber='"+earNumber.get("number")+"' and batch.id='"+batchid+"'").get(0);
//				if(ear==null){
//					resultJson.put("code", "1105");
//					resultJson.put("msg","对应批次中不存在耳标号"+earNumber.get("number"));
//					return resultJson.toString();
//				}
//				if(!(ear.getStatus().equals(EarTagStatusEnum.USE)||ear.getStatus().equals(EarTagStatusEnum.TRADE))){
//					resultJson.put("code", "1105");
//					resultJson.put("msg","耳标号"+earNumber.get("number")+"状态不是使用中/补换标");
//					return resultJson.toString();
//				}
//				eteInfo.setEarNumber(ear);
//				eteInfo.setScanDate(sdf.parse(earNumber.getString("scanDate")));
//				eteInfo.setScaner(uInfo);
//				info.getEarTagEntry().add(eteInfo);
//
//				if(StringUtils.isNotBlank(earNumber.getString("qrcode"))){
//					EarTagQrCodeStoreInfo qrcode = EarTagQrCodeStoreFactory.getLocalInstance(ctx).getEarTagQrCodeStoreCollection("where qrCode='" + earNumber.get("qrcode") + "' and batch.id='" + batchid + "'").get(0);
//					if (qrcode == null) {
//						resultJson.put("code", "1105");
//						resultJson.put("msg","对应批次中不存在二维码" + earNumber.get("qrcode"));
//						return resultJson.toString();
//					}
//					if (!(qrcode.getStatus().equals(EarTagStatusEnum.USE)||qrcode.getStatus().equals(EarTagStatusEnum.TRADE))) {
//						resultJson.put("code", "1105");
//						resultJson.put("msg","二维码" + earNumber.get("qrcode") + "状态不是使用中/补换标");
//						return resultJson.toString();
//					}
//					eteInfo.setQrcode(qrcode);
//				}
//			}
//			info.setEarTagQty(earNumbers.size());
//		}
//
////		JSONArray weighList = jsons.getJSONArray("weighList"); //称重记录
////		if(weighList!=null){
////			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////			for(int i=0; i<weighList.size(); i++){
////				JSONObject weighJson = weighList.getJSONObject(i);
////				PoundCheckWeihtListInfo pwlInfo = new PoundCheckWeihtListInfo();
////				Date createTime = sdf.parse(weighJson.getString("createTime"));
////				pwlInfo.setCreateTime(new Time(createTime.getTime())); //取数时间
////				pwlInfo.setCustomer(UIRuleUtil.getString(weighJson.get("customer"))); //客户名
////				pwlInfo.setCustomerNo(UIRuleUtil.getString(weighJson.get("customerNo"))); //客户号
////				pwlInfo.setGood(UIRuleUtil.getString(weighJson.get("good"))); //货物名
////				pwlInfo.setGoodNo(UIRuleUtil.getString(weighJson.get("goodNo"))); //货物号
////				pwlInfo.setGross(UIRuleUtil.getBigDecimal(weighJson.get("gross"))); //毛重
////				pwlInfo.setZtid(UIRuleUtil.getString(weighJson.get("id"))); //中台主键
////				pwlInfo.setIsDelete(UIRuleUtil.getBooleanValue(weighJson.get("isDelete"))); //删除标识符
////				pwlInfo.setNet(UIRuleUtil.getBigDecimal(weighJson.get("net"))); //净重
////				pwlInfo.setNumber(UIRuleUtil.getString(weighJson.get("number"))); //供应商设备编码
////				pwlInfo.setTare(UIRuleUtil.getBigDecimal(weighJson.get("tare"))); //皮重
////				pwlInfo.setTruck(UIRuleUtil.getString(weighJson.get("truck"))); //车牌号
////				Date weighTime = sdf.parse(weighJson.getString("weighTime"));
////				pwlInfo.setWeighTime(new Time(weighTime.getTime())); //称重时间
////				info.getWeihtList().add(pwlInfo);
////			}
////		}
//
//		if(submitType>0){
//			EarTagPoolInfo pool=EarTagPoolFactory.getLocalInstance(ctx).getEarTagPoolCollection("where batch.id='"+info.getBthno().getId()+"'").get(0);
//			if(pool!=null&&pool.getCountQty()-pool.getEnableQty()-pool.getDisableQty()>0){
//				if(info.getEarTagEntry().size()==0){
//					resultJson.put("code", "1105");
//					resultJson.put("msg","该批次已上线电子耳标，必须录入！");
//					return resultJson.toString();
//				}
//			}
//		}
//
//		// 删除废弃的图片
//		BybUtils.deleteFile(ctx, jsons.getJSONArray("delImgs"));
//
//		JSONArray ja = jsons.getJSONArray("checkImgs"); // 猪车过磅检查表
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "01", tempFiles,
//					attachmentCols, info, ja, "checkImgs", "猪车过磅检查表"));
//			attachmentCols.clear();
//		}
//
//		ja = jsons.getJSONArray("carImgs"); // 驾驶室照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "02", tempFiles,
//					attachmentCols, info, ja, "carImgs", "机打过磅单拍照"));
//			attachmentCols.clear();
//		}
//
//		ja = jsons.getJSONArray("toolsImgs"); // 工具箱照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "03", tempFiles,
//					attachmentCols, info, ja, "toolsImgs", "销售清单拍照"));
//			attachmentCols.clear();
//		}
//
//		ja = jsons.getJSONArray("underCarImgs"); // 车身底部照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "04", tempFiles,
//					attachmentCols, info, ja, "underCarImgs", "车身底部照片"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("pigStartImgs"); // 控料开始料槽照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "05", tempFiles,
//					attachmentCols, info, ja, "pigStartImgs", "控料开始料槽照"));
//			attachmentCols.clear();
//		}
//
//		ja = jsons.getJSONArray("pigEndImgs"); // 开始装猪料槽照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "06", tempFiles,
//					attachmentCols, info, ja, "pigEndImgs", "开始装猪料槽照"));
//			attachmentCols.clear();
//		}
//
//		ja = jsons.getJSONArray("headCarImgs"); // 车头检查照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "07", tempFiles,
//					attachmentCols, info, ja, "headCarImgs", "车头检查照片"));
//			attachmentCols.clear();
//		}
//
//		//2019-7-24 邱 生物安全
//		ja = jsons.getJSONArray("cleanImgs"); // 车头检查照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "08", tempFiles,
//					attachmentCols, info, ja, "cleanImgs", "车辆是否干净拍照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("serviceImgs"); // 车头检查照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "09", tempFiles,
//					attachmentCols, info, ja, "serviceImgs", "服务部洗消点车体消毒/驾驶室消毒/烘干拍照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("yhImgs"); // 车头检查照片
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "10", tempFiles,
//					attachmentCols, info, ja, "yhImgs", "养户一级洗消点冲洗/车体消毒/驾驶室消毒拍照"));
//			attachmentCols.clear();
//		}
//		//end
//		//新增图片
//		ja = jsons.getJSONArray("cabImgs"); // 驾驶室照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "11", tempFiles,
//					attachmentCols, info, ja, "cabImgs", "驾驶室照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("fuelMeterImgs"); // 油码表照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "12", tempFiles,
//					attachmentCols, info, ja, "fuelMeterImgs", "油码表照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("toolboxInteriorImgs"); // 工具箱内部照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "13", tempFiles,
//					attachmentCols, info, ja, "toolboxInteriorImgs", "工具箱内部照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("tankImgs"); // 油箱照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "14", tempFiles,
//					attachmentCols, info, ja, "tankImgs", "油箱照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("waterRaftImgs"); // 水箱打开水筏照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "15", tempFiles,
//					attachmentCols, info, ja, "waterRaftImgs", "水箱打开水筏照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("panelImgs"); // 车箱第一层面板照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "16", tempFiles,
//					attachmentCols, info, ja, "panelImgs", "车箱第一层面板照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("spareTireImgs"); // 悬挂的备胎照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "17", tempFiles,
//					attachmentCols, info, ja, "spareTireImgs", "悬挂的备胎照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("canvasImgs"); // 车载帆布照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "18", tempFiles,
//					attachmentCols, info, ja, "canvasImgs", "车载帆布照片"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("weighingImgs"); // 过皮称重照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "19", tempFiles,
//					attachmentCols, info, ja, "weighingImgs", "过皮称重照"));
//			attachmentCols.clear();
//		}
////		ja = jsons.getJSONArray("materialControlImgs"); // 控料开始料槽照
////		if (ja != null && ja.size() > 0)
////		{
////			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "20", tempFiles,
////					attachmentCols, info, ja, "materialControlImgs", "控料开始料槽照"));
////			attachmentCols.clear();
////		}
////		ja = jsons.getJSONArray("pigTroughImgs"); // 开始装猪料槽照
////		if (ja != null && ja.size() > 0)
////		{
////			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "21", tempFiles,
////					attachmentCols, info, ja, "pigTroughImgs", "开始装猪料槽照"));
////			attachmentCols.clear();
////		}
//		ja = jsons.getJSONArray("grossWeightImgs"); // 过毛重照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "22", tempFiles,
//					attachmentCols, info, ja, "grossWeightImgs", "过毛重照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("defectImgs"); // 外观缺陷猪只照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "23", tempFiles,
//					attachmentCols, info, ja, "defectImgs", "外观缺陷猪只照"));
//			attachmentCols.clear();
//		}
//		ja = jsons.getJSONArray("redHairedPigImgs"); // 红毛猪照
//		if (ja != null && ja.size() > 0)
//		{
//			attachmentColslits.addCollection(uploadImgUtil(ctx, uInfo, "24", tempFiles,
//					attachmentCols, info, ja, "redHairedPigImgs", "红毛猪照"));
//			attachmentCols.clear();
//		}
//
//		//新增图片	结束
//
//
//		if (attachmentColslits.size() > 0)
//		{
//			//			if(null != uInfo && null != uInfo.getId())
//			//			{
//			//				ctx.put("UserInfo", uInfo);
//			//			    ctx.setCaller(new ObjectUuidPK(uInfo.getId()));
//			//			}
//			AttachmentFacadeFactory.getLocalInstance(ctx).addNewAttachment(attachmentColslits);
//		}
//		if (jsons.containsKey("rcordid") && jsons.getString("rcordid") != null)
//		{
//			String id = jsons.getString("rcordid");
//			info.setLastUpdateUser(uInfo);
//			info.setLastUpdateTime(new Timestamp(new Date().getTime()));
//			info.setAuditor(uInfo);
//			info.setAuditTime(new Timestamp(new Date().getTime()));
//			pk2 = new ObjectUuidPK(id);
//			if(submitType!=1)
//			{
//				PoundCheckFactory.getLocalInstance(ctx).update(pk2, info);
//			}
//		}
//		else
//		{
//			pk2=PoundCheckFactory.getLocalInstance(ctx).addnew(info);
//		}
//
//		//保存
//		if(submitType == 4){
//			PoundCheckFactory.getLocalInstance(ctx).submit(info);
//		}
//		//发消息
//		if(submitType == 2){
//			PoundCheckFactory.getLocalInstance(ctx).submit(info);
//			StringBuffer html=new StringBuffer();
//			html.append("<!DOCTYPE> \r\n");
//			html.append("<html> \r\n");
//			html.append("<head> \r\n");
//			html.append("<meta charset=\"UTF-8\"> \r\n");
//			html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \r\n");
//			html.append("<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\"> \r\n");
//			html.append("<title></title> \r\n");
//			html.append("	<style> \r\n");
//			html.append("	.cont{ \r\n");
//			html.append("		background:white; \r\n");
//			html.append("		border-radius: 5px; \r\n");
//			html.append("		overflow: hidden; \r\n");
//			html.append("	} \r\n");
//			html.append("	.title{ \r\n");
//			html.append("		background:#4b7ac3; \r\n");
//			html.append("		color:white; \r\n");
//			html.append("		padding:10px; \r\n");
//			html.append("	}");
//			html.append("	.text{ \r\n");
//			html.append("	 	color:#555333; \r\n");
//			html.append("	 	padding:10px; \r\n");
//			html.append("	} \r\n");
//			html.append("   </style> \r\n");
//			html.append("	</head> \r\n");
//			html.append("	<body style=\"background:#e6e6e6\"> \r\n");
//			html.append("	<div class=\"cont\"> \r\n");
//			html.append("		<div class=\"title\">销售监磅提醒</div>	\r\n");
//			html.append("		<div class=\"text\">	\r\n");
//			html.append("			<div>服务部："+batchInfo.getServiceDepart().getServiceName()+"</div> \r\n");
//			html.append("			<div>批次："+batchInfo.getBatchName()+"</div> \r\n");
//			html.append("			<div>单据号："+info.getNumber()+"</div> \r\n");
//			BigDecimal tare = UIRuleUtil.isNull(info.getTare())?BigDecimal.ZERO:info.getTare();//空车皮重
//			BigDecimal saveTare = UIRuleUtil.isNull(info.getSaveTare())?BigDecimal.ZERO:info.getSaveTare();//备案皮重
//
//			html.append("			<div style=\"margin:5px 0 5px 0\">销售均重与上市估重偏差<font style=\"color:red;font-weight:bold\">"+info.getOnlineSub()+"</font>kg，销售均重与系统估重偏差<font style=\"color:red;font-weight:bold\">"+info.getSystemSub()+"</font>kg，空车皮重超备案皮重<font style=\"color:red;font-weight:bold\">"+tare.subtract(saveTare)+"</font>kg，请前往现场核实。</div> \r\n");
//			html.append("			<div style=\"color:#666777\">时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"</div> \r\n");
//			html.append("		</div> \r\n");
//			html.append("	</div>	\r\n");
//			html.append("	</body> \r\n");
//			html.append("</html> \r\n");
//
//
//			EntityViewInfo ev = new EntityViewInfo();
//			FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("adminOrgUnit.id",info.getStorageOrgUnit().getId().toString()));
//			ev.setFilter(filter);
//			SelectorItemCollection sic1=new SelectorItemCollection();
//			sic1.add(new SelectorItemInfo("*"));
//			sic1.add(new SelectorItemInfo("parent.id"));
//			sic1.add(new SelectorItemInfo("parent.parent.id"));
//			ev.setSelector(sic1);
//			ManageOrgInfo moi=ManageOrgFactory.getLocalInstance(ctx).getManageOrgCollection(ev).get(0);
//			if(moi!=null){
//				boolean isSupper=false;
//				String msg=batchInfo.getServiceDepart().getServiceName()+"，"+batchInfo.getBatchName()+"，"+info.getNumber()+"，销售均重与上市估重偏差"+info.getOnlineSub()+"kg，销售均重与系统估重偏差"+info.getSystemSub()+"kg，空车皮重超备案皮重"+tare.subtract(saveTare)+"kg，请前往现场核实。";
//				//上市估重超5公斤
//				if(jsons.getBigDecimal("onlineSub").abs().compareTo(new BigDecimal("5"))>0||tare.subtract(saveTare).compareTo(new BigDecimal("20"))>0||tare.subtract(saveTare).compareTo(new BigDecimal("100"))<0){
//					isSupper=true;
//					// 服务部主任
//					ManageOrgPositionPositionUserCollection moppuis3=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='"+moi.getId()+"' and parent.position.id in ('Va4AAAh63SEMZM+n')  and person.id is not null");
//					for(int i=0;i<moppuis3.size();i++){
//						UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppuis3.get(i).getPerson().getId()+"'").get(0);
//						EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//					}
//					// 分公司经理\分公司财务副经理\分公司销售主任
//					if(moi.getParent()!=null){
//						ManageOrgPositionPositionUserCollection moppui2=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='"+moi.getParent().getId()+"' and parent.position.id in ('Va4AAAEkOKoMZM+n','Va4AAACmKeUMZM+n','Va4AAACpJrwMZM+n') and person.id is not null");
//						for(int i=0;i<moppui2.size();i++){
//							UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppui2.get(i).getPerson().getId()+"'").get(0);
//							EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//						}
//
//					}
//					//事业部稽核经理
//					ManageOrgPositionPositionUserCollection moppui2=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='Va4AAAAsocjtzlUp' and parent.position.id in ('Va4AAArIugoMZM+n') and person.id is not null");
//					for(int i=0;i<moppui2.size();i++){
//						UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppui2.get(i).getPerson().getId()+"'").get(0);
//						EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//					}
//				}
//
//				//与系统推算均重差10公斤
//				if(jsons.getBigDecimal("systemSub").abs().compareTo(new BigDecimal("10"))>0){
//					if(!isSupper){
//						// 服务部主任
//						ManageOrgPositionPositionUserCollection moppuis3=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='"+moi.getId()+"' and parent.position.id in ('Va4AAAh63SEMZM+n')  and person.id is not null");
//						for(int i=0;i<moppuis3.size();i++){
//							UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppuis3.get(i).getPerson().getId()+"'").get(0);
//							EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//						}
//					}
//					if(moi.getParent()!=null){
//
//						if(moi.getParent().getParent()!=null){
//							// 片区财务总监、片区总经理、片区销售总监
//							ManageOrgPositionPositionUserCollection moppuis=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='"+moi.getParent().getParent().getId()+"' and parent.position.id in ('Va4AAAI5uVUMZM+n','Va4AAACmKdEMZM+n','Va4AAACmKiwMZM+n')  and person.id is not null");
//							for(int i=0;i<moppuis.size();i++){
//								UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppuis.get(i).getPerson().getId()+"'").get(0);
//								EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//							}
//						}
//
//					}
//
//					//事业部稽核经理\事业部销售部财务副总监
//					ManageOrgPositionPositionUserCollection moppui2=ManageOrgPositionPositionUserFactory.getLocalInstance(ctx).getManageOrgPositionPositionUserCollection("where parent.parent1.id='Va4AAAAsocjtzlUp' and parent.position.id in ("+(isSupper?"":"'Va4AAArIugoMZM+n',")+"'Va4AAAnHUOUMZM+n') and person.id is not null");
//					for(int i=0;i<moppui2.size();i++){
//						UserInfo user=UserFactory.getLocalInstance(ctx).getUserCollection("where person.id='"+moppui2.get(i).getPerson().getId()+"'").get(0);
//						EnterpriseWXMsgUtil.sendMsg(user.getNumber(),"35", "【销售监磅提醒】", html.toString(), msg);
//					}
//				}
//			}
//		}
//
//		//提交
//		if (submitType == 1)
//		{
//			SaleBillInfo saleInfo = new SaleBillInfo(); // 销售单
//			SaleBillEntryInfo saleEntryInfo = new SaleBillEntryInfo(); // 销售单分录
//			YZ_RH_CUSTInfo feederInfo = new YZ_RH_CUSTInfo();
//			saleInfo.setSourceFunction(info.getId().toString());
//
//			if (shipmentID != null)
//			{
//				saleshipInfo = SaleShipmentDetailFactory.getLocalInstance(ctx)
//						.getSaleShipmentDetailInfo(new ObjectUuidPK(shipmentID));
//
//				shipmentInfo = SaleShipmentFactory.getLocalInstance(ctx).getSaleShipmentInfo(
//						new ObjectUuidPK(saleshipInfo.getParent().getId()));
//
//				com.kingdee.eas.zbsd.zbsdbizbill.SaleBillInfo sbInfo = SaleBillFactory
//						.getLocalInstance(ctx).getSaleBillInfo(
//								new ObjectUuidPK(shipmentInfo.getSourceBillId().toString()));
//				saleInfo.setSourceBillId(shipmentInfo.getId().toString());
//
//				SaleCustomerInfo salecustomInfo = new SaleCustomerInfo();
//				if (shipmentInfo.getCust() != null)
//				{
//					String customid = shipmentInfo.getCust().getId().toString(); // 获取销售客户ID
//					salecustomInfo = SaleCustomerFactory.getLocalInstance(ctx)
//							.getSaleCustomerInfo(new ObjectUuidPK(customid));
//					if(salecustomInfo.getCust()!=null)
//					{
//						CustomerInfo custInfo = CustomerFactory
//								.getLocalInstance(ctx)
//								.getCustomerInfo(new ObjectUuidPK(salecustomInfo.getCust().getId()));
//						saleInfo.setCustoner(custInfo); // 设置客户
//					}
//					else
//					{
//						resultJson.put("code", "1105");
//						resultJson.put("msg", "单据编号:"+salecustomInfo.getNumber()+"销售客户档案中的客户信息不能为空");
//						return resultJson.toString();
//					}
//				}
//				if((sbInfo != null)&&(sbInfo.getSalesMan()!=null))
//				{
//					PersonInfo personInfo = new PersonInfo();
//					personInfo = PersonFactory.getLocalInstance(ctx).getPersonInfo(
//							new ObjectUuidPK(sbInfo.getSalesMan().getId()));
//					saleInfo.setSalesmanID(personInfo.getId().toString());
//					saleInfo.setSalesman(personInfo.getName()); // 设置销售对接员ID以及姓名
//				}
//				saleInfo.setCompanyOrgUnit(sbInfo.getCompanyOrgUnit());
//				saleInfo.setVerifyCapital(sbInfo.getVerifyCapital());
//			}
//			saleInfo.setStorageUnit(batchInfo.getStorageOrgUnit());
//			saleInfo.setContract(batchInfo.getContract()); // 设置销售单合同编号
//			saleInfo.setCreator(uInfo);
//			saleInfo.setCreateTime(new Timestamp(new Date().getTime()));
//			saleInfo.setBizDate(new Date());
//			saleInfo.setSaleDate(new Date());
//			saleInfo.setBillStatus(BillStatesEnum.save);
//			saleInfo.setId(BOSUuid.create(saleInfo.getBOSType()));
//			saleInfo.setStorageOrgUnit(info.getServiceDept());
//			if (Util.isCodeRuleEnable(ctx, saleInfo))
//			{
//				String number = Util.getAutoCode(ctx, saleInfo);
//				saleInfo.setNumber(number);
//			}
//			saleInfo.setSaleBillCreateType(SaleBillCreateType.saleShipmentCreate); // 通知单生成订单
//			saleInfo.setBatchNo(batchInfo); // 设置批次档案
//			saleInfo.setCarMessage(info.getCarData()); // 设置车辆信息
//			if (batchInfo.getUserinfo() != null)
//			{
//				String feederid = batchInfo.getUserinfo().getId().toString();
//
//				feederInfo = YZ_RH_CUSTFactory.getLocalInstance(ctx).getYZ_RH_CUSTInfo(
//						new ObjectUuidPK(feederid));
//				saleInfo.setCust(feederInfo); // 设置养户
//			}
//			saleInfo.setLoadometerType(info.getWeighbridge()); // 设置磅码类型
//			saleInfo.setStartTime(info.getPigStartTime());
//			saleInfo.setEndTime(info.getPigEndTime());
//			if (info.getPigStartTime() != null && info.getPigEndTime() != null)
//			{
//				long i = (info.getPigEndTime().getTime() - info.getPigStartTime().getTime());
//				double a = i / (1000 * 60);
//				saleInfo.setTimeM(new BigDecimal(a)); // 装猪时间
//			}
//			ThingTypeCollection thingTypeCollection = ThingTypeFactory.getLocalInstance(ctx)
//					.getThingTypeCollection("where name like '外销%' and thingsType='1'");
//			if (thingTypeCollection != null && thingTypeCollection.size() > 0)
//			{
//				saleInfo.setTingType(thingTypeCollection.get(0));
//			}
//			saleEntryInfo.setPrice(info.getPrice().multiply(new BigDecimal(2)));
//			saleEntryInfo.setAmount(info.getAmount());
//			saleEntryInfo.setQty(info.getQty());
//			saleEntryInfo.setAvgWeight(info.getAvgWeight());
//			if(info.getQty()>0)
//			{
//				double amount = info.getAmount().doubleValue();
//				double averagePrice = amount / info.getQty();
//				saleEntryInfo.setAveragePrice(new BigDecimal(averagePrice));
//			}
//			saleEntryInfo.setHeadWeight(info.getAvgWeight());
//			saleEntryInfo.setTotalWerght(info.getNetWeight());
//			saleEntryInfo.setVariety(info.getVariety());
//			saleEntryInfo.setSaleType(varietyInfo.getPigSaletype());
//			saleEntryInfo.setSaleShipDetailID(shipmentID);
//			saleInfo.getEntrys().add(saleEntryInfo);
//			PoundCheckZJEntryCollection pcol=info.getZJEntry();
//			if(pcol!=null && pcol.size()>0)
//			{
//				for(int i = 0;i<pcol.size();i++)
//				{
//					SaleBillAddSubstractEntryInfo saleAddEntryInfo = new SaleBillAddSubstractEntryInfo(); //增补款项目
//					PoundCheckZJEntryInfo pcInfo = pcol.get(i);	 //增补款项目
//					saleAddEntryInfo.setParent(saleInfo);
//					saleAddEntryInfo.setMoney(pcInfo.getMoney());
//					saleAddEntryInfo.setZjxm(pcInfo.getZjxm());
//					saleAddEntryInfo.setYsfs(pcInfo.getYsfs());
//					saleInfo.getAddSubstractEntry().add(saleAddEntryInfo);
//				}
//			}
//
//			for(int i=0;i<info.getEarTagEntry().size();i++){
//				SaleBillEarTagEntryInfo sbete=new SaleBillEarTagEntryInfo();
//				sbete.setEarNumber(info.getEarTagEntry().get(i).getEarNumber());
//				sbete.setQrcode(info.getEarTagEntry().get(i).getQrcode());
//				sbete.setScanDate(info.getEarTagEntry().get(i).getScanDate());
//				sbete.setScaner(info.getEarTagEntry().get(i).getScaner());
//				saleInfo.getEarTagEntry().add(sbete);
//			}
//			saleInfo.setEarTagQty(saleInfo.getEarTagEntry().size());
//
//			//控料时长计算
//			if(null != date2 && null != date1){
//				long time = date1.getTime();
//				long time2 = date2.getTime();
//				long materialControl = time2-time;
//				long t = materialControl / (1000 * 60 * 60 * 24);
//				long s = (materialControl % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//				long f = (materialControl % (1000 * 60 * 60)) / (1000 * 60);
//				long m = (materialControl % (1000 * 60)) / 1000;
//
//				saleInfo.setMaterialControl(t+"天"+s+"时"+f+"分"+m+"秒");
//			}
//			//控料时长计算	结束
//
//			IObjectPK pk = com.kingdee.eas.custom.producebusiness.SaleBillFactory.getLocalInstance(ctx).addnew(saleInfo);
//			ToolBoxFacadeFactory.getLocalInstance(ctx).createBOTPRelation(pk2.toString(), pk.toString());
//			ToolBoxFacadeFactory.getLocalInstance(ctx).createBOTPRelation(shipmentInfo.getId().toString(), pk.toString());
//			info.setBillStatus(BillStatesEnum.audit);
//			info.setAuditor(uInfo);
//			info.setAuditTime(new Timestamp(new Date().getTime()));
//			PoundCheckFactory.getLocalInstance(ctx).update(pk2, info);
//		}
//		resultJson.put("data", info.getId().toString());
//		return resultJson.toString();
//	}
//
//	/**
//	 * Author: caochenghao Effect : 上传图片公用方法
//	 */
//	private static CoreBaseCollection uploadImgUtil(Context ctx, UserInfo uInfo, String num,
//			ArrayList<File> tempFiles, CoreBaseCollection attachmentCols, PoundCheckInfo info,
//			JSONArray ja, String type, String imgContent) throws Exception {
//		CoreBaseCollection attachmentColslits = new CoreBaseCollection();
//
//		if (ja != null)
//		{
//			CtrlUnitInfo ctrlUnitInfo = new CtrlUnitInfo();
//			ctrlUnitInfo.setId(BOSUuid.read("00000000-0000-0000-0000-000000000000CCE7AED4"));
//			Timestamp creatTime = new Timestamp(new Date().getTime());
//			for (int index = 0; index < ja.size(); index++)
//			{
//				JSONObject object = ja.getJSONObject(index);
//				if(null == object.get("fileName"))
//				{
//					continue;
//				}
//				String mainname = "";
//				String extname = "";
//				String fileName = object.getString("fileName");
//				String remotePath = object.getString("fileName");
//				if(!"".equals(fileName)&&fileName.indexOf("/")>-1){
//					String[] remotePaths = fileName.split("/");
//					fileName = remotePaths[remotePaths.length - 1];
//				}
//
//				if(!"".equals(fileName)&&fileName.indexOf(".")>-1){
//					String[] fileNames = fileName.split("\\.");
//					mainname = fileNames[0];
//					extname = fileNames[1];
//				}else{
//					mainname = fileName;
//				}
//
//				AttachmentInfo att = new AttachmentInfo();
//				att.setName(mainname);
//				att.setRemotePath(remotePath);
//				att.setSize(StringUtil4File.getFileSizeRepresentation(object.getIntValue("length")));
//				att.setSizeInByte(object.getIntValue("length"));
//				att.setType(object.getString("imgType"));
//				int fileType = object.getIntValue("fileType");
//				if(1 == fileType)
//				{
//					att.setStorageType(AttachmentStorageTypeEnum.FTP);
//				}else if(3 == fileType)
//				{
//					att.setStorageType(AttachmentStorageTypeEnum.OBS);
//				}
//
//				att.setNumber(mainname);
//				att.setFile(null);
//				att.setAttachID(mainname);
//				att.setIsShared(false);
//				att.getBoAttchAsso().add(makeBoAttchAssoInfo(info.getId().toString(), ctx));
//				att.setSimpleName(extname);
//				att.setCreator(uInfo);
//				att.setCreateTime(creatTime);
//				att.setLastUpdateUser(uInfo);
//				att.setLastUpdateTime(creatTime);
//				att.setCU(ctrlUnitInfo);
//				attachmentCols.add(att);
//			}
//			attachmentColslits.addCollection(attachmentCols);
//			for (int i = 0; i < attachmentCols.size(); i++)
//			{
//				String imgId = ((AttachmentInfo)attachmentCols.get(i)).getAttachID();// 附件ID
//				PoundCheckEntryInfo entryInfo = new PoundCheckEntryInfo();
//				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
//				entryInfo.setParent(info);
//				entryInfo.setImgId(imgId);
//				entryInfo.setImgContent(imgContent);
//				entryInfo.setImgType(type);
//				entryInfo.setImgName(imgId);
//				info.getEntry().add(entryInfo);
//			}
//			attachmentCols.clear();
//		}
//
//		return attachmentColslits;
//	}
//
//	private static BoAttchAssoInfo makeBoAttchAssoInfo(String boID, Context ctx)
//	{
//		BoAttchAssoInfo baai = new BoAttchAssoInfo();
//		baai.setBoID(boID);
//		baai.setAssoType(Resrcs.getString("NewAttachment", ctx.getLocale()));
//		baai.setAssoBusObjType(BOSUuid.read(boID).getType().toString());
//		return baai;
//	}
//
//	// 查询车辆过磅检查
//	public static String selectWeighCheck(Context ctx, JSONObject json) throws SQLException, EASBizException, BOSException
//	{
//		JSONObject resultJson = new JSONObject();
//		JSONObject jsons = json.getJSONObject("data");
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "success");
//		String userName = jsons.getString("userName");
//		String starttime = jsons.getString("starttime");
//		String endtime = jsons.getString("endtime");
//		int pageNumber = jsons.getIntValue("pageNumber");
//		int pageSize = jsons.getIntValue("pageSize");
//		int beginIndex = (pageNumber - 1) * pageSize + 1;
//		if (pageNumber != 1)
//		{
//			pageSize = pageNumber * pageSize;
//		}
//		com.kingdee.eas.base.permission.UserInfo uInfo = null;
//		JSONArray ja = new JSONArray();
//
//		Set<String> ids = new HashSet<String>();
//		uInfo = UserFactory.getLocalInstance(ctx)
//				.getUserInfo("where number='" + userName + "'");
//		String serviceDepts = SaleNoticeService.salerGetServiceDepts(ctx, uInfo);
//		if (serviceDepts == null)
//		{
//			resultJson.put("code", "1105");
//			resultJson.put("msg", "该用户不是电子销售平台管理员,请确认!");
//			return resultJson.toString();
//		}
//
//		StringBuilder sql = new StringBuilder();
//		sql.append(" /*dialect*/ ");
//		sql.append(" select * from ( select rownum rn,tt.* from ( ");
//		sql.append("select a.fid,a.cftare,a.cfgrossweight,cfqty,a.cfnetweight,a.cfavgweight,b.fname_l2 carNumber, "
//				+ "d.fname_l2 as varietyName,round(a.cfamount,2) as cfamount,a.cfprice,a.cfpigstarttime,a.cfpigendtime,a.cfweighbridge,b.fid as carid, "
//				+ "a.cfvarietyid,a.fsourcebillid,a.fdescription,case when f.fname_l2 like '%次品%' then 1 else 0 end as cp, "
//				+ "case when j.fname_l2 like '%次品%' then 1 else 0 end as oldCp,g.fsaleproductid as oldVarId, "
//				+ "to_char(a.fbizdate,'yyyy-MM-dd hh24:mi:ss') bizdate,a.cfsavetare,nvl(e.maxdiff,0) as maxdiff,a.CFBthnoID,c.CFBatchName,a.fsystemSub,a.fonlineSub,a.fbillstatus "
//				//图片字段
//				+ " ,a.FScale scale "
//				+ " ,a.FTankQty TankQty "
//				+ " ,a.FSpareTireQty SpareTireQty "
//				+ " ,a.FCanvasQty CanvasQty "
////				+ " ,a.FIsShadeCloth IsShadeCloth, a.FServiceDeptID serviceid, a.FDeviceId deviceId,a.FDeviceNumber deviceNumber, a.FDeviceName deviceName "
////				//图片字段	结束
////				+ " from CT_PRO_PoundCheck a "
//				+ " ,a.FIsShadeCloth IsShadeCloth "
//				//图片字段	结束
//				+ "from CT_PRO_PoundCheck a "
//				+ "left join CT_BAS_CarData b on a.cfcardataid = b.fid "
//				+ "left join CT_PRO_Yz_rh_batch c on a.CFBthnoID = c.fid "
//				+ "left join CT_BAS_PigSaleVarieties d on d.fid = a.cfvarietyid "
//				+ "inner join CT_BAS_RecoveryVariety f on f.fid = d.CFRecyclerVarietyI "
//				+ "left join T_ZB_SD_SaleShipmentDetail g on g.fid = a.fsourcebillid "
//				+ "left join CT_BAS_PigSaleVarieties h on h.fid = g.fsaleproductid "
//				+ "left join CT_BAS_RECOVERYVARIETY j on j.fid = h.cfrecyclervarietyi "
//				+ "left join ( "
//				+ "  select a.CFCARDATAID as carid,\n"
//				+ "         b.FNAME_L2,\n"
//				+ "         max(abs(a.CFTARE-b.CFBIMUDF0001))  as  maxdiff\n"
//				+ "  from CT_PRO_PoundCheck a\n"
//				+ "  inner join CT_BAS_CarData b on a.CFCARDATAID = b.fid \n"
//				+ "  where a.FBILLSTATUS=30 \n"
//				+ "  group by a.CFCARDATAID,b.FNAME_L2\n"
//				+ ") e on e.carid = b.fid \n"
//				+ "where a.fbillstatus <> 90 and c.CFServiceDepartID in ("+ serviceDepts + ") ");
//		if ((!("").equals(starttime) && null != starttime)
//				|| (!("").equals(endtime) && null != endtime))
//		{
//			sql.append(" and to_char(a.fCreateTime,'yyyy-mm-dd') between '" + starttime
//					+ "' and '" + endtime + "' ");
//		}
//		else
//		{
//			if (null != starttime && (!("").equals(starttime)))
//			{
//				sql.append(" and to_char(a.fCreateTime,'yyyy-mm-dd') = '" + starttime + "'   ");
//			}
//			if (null != endtime && !("").equals(endtime))
//			{
//				sql.append(" and to_char(a.fCreateTime,'yyyy-mm-dd') = '" + endtime + "'   ");
//			}
//		}
//		sql.append(" order by a.fCreateTime desc ").append(
//				" ) tt ) rr where   rn >= " + beginIndex + "  and rn <=  " + pageSize + "  ");
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//		ISaleBill Isalebill = com.kingdee.eas.custom.producebusiness.SaleBillFactory
//				.getLocalInstance(ctx);
//		SaleBillCollection salecol;
//		JSONArray tempja = new JSONArray();
//
//		ISaleShipmentDetail IShipmentDetail = SaleShipmentDetailFactory.getLocalInstance(ctx);
//		while (rs.next())
//		{
//			JSONObject obj = new JSONObject();
//			//图片字段
//			obj.put("fscale", rs.getString("scale"));
//			obj.put("ftankqty", rs.getString("TankQty"));
//			obj.put("fsparetireqty", rs.getString("SpareTireQty"));
//			obj.put("fcanvasqty", rs.getString("CanvasQty"));
//			obj.put("fisshadecloth", "1".equals(rs.getString("IsShadeCloth"))?true:false);
//			//图片字段	结束
//			obj.put("rcordid", rs.getString("fid"));
//			ids.add(rs.getString("fid"));
//			obj.put("batchid", rs.getString("CFBthnoID"));
//			obj.put("tare", rs.getString("cftare"));
//			obj.put("batchno", rs.getString("CFBatchName"));
//			obj.put("grossWeight", rs.getString("cfgrossweight"));
//			obj.put("qty", rs.getString("cfqty"));
//			obj.put("netWeight", rs.getString("cfnetweight"));
//			obj.put("avgWeight", rs.getString("cfavgweight"));
//			obj.put("carNumber", rs.getString("carnumber"));
//			obj.put("saveTare", rs.getString("cfsavetare"));
//			obj.put("bizdate", rs.getString("bizdate"));
//			obj.put("varietyName", rs.getString("varietyName")); // 销售品种
//			obj.put("totalAmount", rs.getString("cfamount"));
//			obj.put("price", rs.getString("cfprice")); // 销售单价
//			obj.put("pigStartTime", rs.getString("cfpigstarttime"));
//			obj.put("pigEndTime", rs.getString("cfpigendtime"));
//			obj.put("carData", rs.getString("carid"));
//			obj.put("maxdiff", rs.getString("maxdiff"));
//			obj.put("loadometerType", rs.getString("cfweighbridge")); // 地磅类型
//
//			obj.put("shipmentID", rs.getString("fsourcebillid")); // customerID
//			obj.put("varietyId", rs.getString("cfvarietyid")); // var
//			obj.put("cp", rs.getString("cp")); // var
//			obj.put("customID", rs.getString("fdescription"));
//			obj.put("oldCp", rs.getString("oldCp"));
//			obj.put("oldVarId", rs.getString("oldVarId"));
//
//			obj.put("systemSub", rs.getString("fsystemSub"));
//			obj.put("onlineSub", rs.getString("fonlineSub"));
//
//			if(StringUtils.isNotBlank(rs.getString("fbillstatus"))){
//				obj.put("billStatusIndex", rs.getString("fbillstatus"));
//				obj.put("billstatus", BillStatesEnum.getEnum(rs.getString("fbillstatus")).getAlias());
//			}
//
//			int saleNum = 0;
//			if (rs.getString("fsourcebillid") != null
//					&& rs.getString("fsourcebillid").length() > 0)
//			{
//				SaleShipmentDetailInfo shipmentInfo = IShipmentDetail
//						.getSaleShipmentDetailInfo(new ObjectUuidPK(rs
//								.getString("fsourcebillid")));
//				saleNum = shipmentInfo.getQty().intValue();
//			}
//			obj.put("saleNum", saleNum);
//
//			salecol = Isalebill.getSaleBillCollection("where sourceFunction = '"
//					+ rs.getString("fid") + "' and billStatus <> 90");
//			if (salecol.size() != 0)
//			{
//				obj.put("statusType", 1); // 不能编辑已经推了销售单
//			}
//			else
//			{
//				obj.put("statusType", 0); // 可以编辑销售单
//			}
//			PoundCheckZJEntryCollection pcSuCol;
//			IPoundCheckZJEntry IPoundEntry =PoundCheckZJEntryFactory.getLocalInstance(ctx);
//			pcSuCol = IPoundEntry.getPoundCheckZJEntryCollection("where parent= '"+rs.getString("fid")+"' ");
//			JSONArray ps = new JSONArray();
//			IAddSubtractType Isubcol = AddSubtractTypeFactory.getLocalInstance(ctx);
//			for(int i = 0 ;i<pcSuCol.size();i++)
//			{
//				JSONObject ob = new JSONObject();
//				PoundCheckZJEntryInfo pinfo = pcSuCol.get(i);
//				AddSubtractTypeInfo subinfo = Isubcol.getAddSubtractTypeInfo(new ObjectUuidPK(pinfo.getZjxm().getId()));
//				ob.put("zjxmid", subinfo.getId().toString());
//				ob.put("zjxm",subinfo.getName());
//				ob.put("ysfs", pinfo.getYsfs().getValue());
//				ob.put("money", pinfo.getMoney());
//				ps.add(ob);
//			}
//			obj.put("zjxmList", ps);
////			obj.put("serviceid", rs.getString("fdescription")); //服务部ID
////			obj.put("deviceId", rs.getString("deviceId")); //电子秤ID
////			obj.put("deviceNumber", rs.getString("deviceNumber")); //电子秤编码
////			obj.put("deviceName", rs.getString("deviceName")); //电子秤别名
//
//			SelectorItemCollection sic=new SelectorItemCollection();
//			sic.add(new SelectorItemInfo("*"));
//			sic.add(new SelectorItemInfo("qrcode.*"));
//			sic.add(new SelectorItemInfo("earNumber.*"));
//			EntityViewInfo ev=new EntityViewInfo();
//			FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("parent.id",rs.getString("fid")));
//			ev.setFilter(filter);
//			ev.setSelector(sic);
//			PoundCheckEarTagEntryCollection etes = PoundCheckEarTagEntryFactory.getLocalInstance(ctx).getPoundCheckEarTagEntryCollection(ev);
//			obj.put("earNumbers", EarTagUtils.parseJSONArray(etes));
//
////			SelectorItemCollection weighsic=new SelectorItemCollection();
////			weighsic.add(new SelectorItemInfo("*"));
////			EntityViewInfo weighev=new EntityViewInfo();
////			FilterInfo weighfilter=new FilterInfo();
////			weighfilter.getFilterItems().add(new FilterItemInfo("parent.id",rs.getString("fid")));
////			weighev.setFilter(weighfilter);
////			weighev.setSelector(weighsic);
////			PoundCheckWeihtListCollection weigh = PoundCheckWeihtListFactory.getLocalInstance(ctx).getPoundCheckWeihtListCollection(weighev);
////			obj.put("weighList", EarTagUtils.parseJSONArray(weigh));
//
//			tempja.add(obj);
//
//		}
//
//		String strIds = com.kingdee.util.StringUtils.arrayToString(ids.toArray(), "','");
//		String sql2 =
//
//				"select distinct b.CFImgType IMGType," + "                c.fboid billId,"
//						+ "                b.fid entrysId,"
//						+ "                a.FSTORAGETYPE filetype, a.fremotepath remotepath"
//						+ "           from T_BAS_Attachment a"
//						+ "          inner join CT_PRO_PoundCheckEntry b"
//						+ "             on b.CFImgId = a.fattachid"
//						+ "          inner join T_BAS_BoAttchAsso c"
//						+ "             on a.fid = c.fattachmentid" + "          where c.fboid in('"
//						+ strIds + "')";
//		rs = DbUtil.executeQuery(ctx, sql2.toString());
//		Map<String, Map<String, List<String>>> imgMap = new HashMap<String, Map<String, List<String>>>();
//		while (rs.next())
//		{
//			int filetype = rs.getInt("filetype");
//			String imgUrl = null;
//			if(1 == filetype)
//			{
//				imgUrl = Util.getAttachmentURL(rs.getString("remotepath")) ;
//			}else if(3 == filetype)
//			{
//				imgUrl = Util.getOBSAttachmentURL(rs.getString("remotepath")) ;
////				//测试用地址
////				try {
////					imgUrl = "http://testservice.zhengbang.com:8800/FILE/showOBS?url=" + URLEncoder.encode(rs.getString("remotepath"), "utf-8");
////				} catch (UnsupportedEncodingException e) {
////					// TODO Auto-generated catch block
////					logger.error(e.getMessage(), e);
////				}
////				//测试用地址	结束
//			}
//			String billId = rs.getString("billId");
//			String type = rs.getString("IMGType");
//
//			Map<String, List<String>> m = imgMap.get(billId) == null
//					? new HashMap<String, List<String>>()
//					: imgMap.get(billId);
//			List<String> imgList = m.get(type) == null ? new ArrayList<String>() : m.get(type);
//			imgList.add(imgUrl);
//			m.put(type, imgList);
//			imgMap.put(billId, m);
//		}
//
//		Map<String, String> keymap = getImgKeymap();
//		for (int i = 0; i < tempja.size(); i++)
//		{
//			JSONObject temp = tempja.getJSONObject(i);
//			String beanid = temp.getString("rcordid");
//			Map<String, List<String>> map = imgMap.get(beanid);
//			if (map != null)
//			{
//				temp = getImgJsonArray(temp, keymap, map);
//			}
//			ja.add(temp);
//		}
//
//
//		resultJson.put("data", ja);
//		return resultJson.toString();
//	}
//
//	// 填充图片列表数据
//	private static JSONObject getImgJsonArray(JSONObject resultJson, Map<String, String> keymap,
//			Map<String, List<String>> map)
//	{
//		Set<String> keySet = keymap.keySet();
//		for (String key : keySet)
//		{
//			if (map.get(key) != null)
//			{
//				JSONArray array = new JSONArray();
//				List<String> list = map.get(key);
//				for (String img : list)
//				{
//					JSONObject obj = new JSONObject();
//					obj.put("img", img);
//					array.add(obj);
//				}
//				resultJson.put(keymap.get(key), array);
//			}
//		}
//		return resultJson;
//	}
//
//	private static Map<String, String> getImgKeymap()
//	{
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("headCarImgs", "headCarImgs");
//		map.put("carImgs", "carImgs");
//		map.put("toolsImgs", "toolsImgs");
//		map.put("underCarImgs", "underCarImgs");
//		map.put("pigStartImgs", "pigStartImgs");
//		map.put("pigEndImgs", "pigEndImgs");
//		map.put("checkImgs", "checkImgs");
//		map.put("cleanImgs", "cleanImgs");
//		map.put("serviceImgs", "serviceImgs");
//		map.put("yhImgs", "yhImgs");
//
//		//新增
//		map.put("cabImgs", "cabImgs");
//		map.put("fuelMeterImgs", "fuelMeterImgs");
//		map.put("toolboxInteriorImgs", "toolboxInteriorImgs");
//		map.put("tankImgs", "tankImgs");
//		map.put("waterRaftImgs", "waterRaftImgs");
//		map.put("panelImgs", "panelImgs");
//		map.put("spareTireImgs", "spareTireImgs");
//		map.put("canvasImgs", "canvasImgs");
//		map.put("weighingImgs", "weighingImgs");
//		map.put("materialControlImgs", "materialControlImgs");
//		map.put("pigTroughImgs", "pigTroughImgs");
//		map.put("grossWeightImgs", "grossWeightImgs");
//		map.put("defectImgs", "defectImgs");
//		map.put("redHairedPigImgs", "redHairedPigImgs");
//		//新增	结束
//		return map;
//	}
//	//查询增补款项目类型
//	public static String selectSupplementType(Context ctx, JSONObject json) throws SQLException, BOSException
//	{
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "success");
//		JSONArray ja = new JSONArray();
//		String sql= "/*dialect*/ select a.fid,a.fname_l2,a.cfysfs from T_ZSB_AddSubtractType a where a.fusedstatus =1 " ;
//
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//
//		while(rs.next())
//		{
//			JSONObject temp = new JSONObject();
//			temp.put("zjxmid",  rs.getString("fid"));
//			temp.put("zjxm", rs.getString("fname_l2"));
//			temp.put("ysfs", rs.getString("cfysfs"));
//			ja.add(temp);
//		}
//		resultJson.put("data", ja);
//
//
//		return resultJson.toString();
//	}
//
//	// 删除车辆过磅检查
//	public static String deleteAnthelmint(Context ctx, JSONObject json) throws BOSException, EASBizException
//	{
//		JSONObject resultJson = new JSONObject();
//		JSONObject jsons = json.getJSONObject("data");
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "success");
//
//		String rcordId = jsons.getString("rcordid");
//		String userName = jsons.getString("userName");
//		PoundCheckInfo applyInfo = PoundCheckFactory.getLocalInstance(ctx).getPoundCheckInfo(
//				new ObjectUuidPK(rcordId));
//		com.kingdee.eas.base.permission.UserInfo uInfo = UserFactory.getLocalInstance(ctx)
//				.getUserInfo("where number='" + userName + "'");
//		applyInfo.setLastUpdateUser(uInfo);
//		applyInfo.setLastUpdateTime(new Timestamp(new Date().getTime()));
//		if (applyInfo.getBillStatus().equals(BillStatesEnum.save)
//				|| applyInfo.getBillStatus().equals(BillStatesEnum.submit))
//		{
//			applyInfo.setBillStatus(BillStatesEnum.delete);
//			PoundCheckFactory.getLocalInstance(ctx)
//					.update(new ObjectUuidPK(rcordId), applyInfo);
//		}
//		else
//		{
//			resultJson.put("code", "1105");
//			resultJson.put("msg", "删除失败,只能删除保存/提交状态的单据!");
//		}
//
//		return resultJson.toString();
//	}
//
//	/**
//	 * 模糊搜索车辆信息
//	 * @throws BOSException
//	 * @throws SQLException
//	 */
//	public static String queryCarData(Context ctx, JSONObject json) throws BOSException, SQLException
//	{
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "查询成功");
//		JSONArray ja = new JSONArray();
//
//		String keyword = json.getString("keyword");
//
//		String sql =	"/*dialect*/ select\n" +
//				"a.fid,\n" +
//				"a.fname_l2,\n" +
//				"nvl(a.CFBIMUDF0001,0) skin ,\n" +
//				"nvl(c.maxdiff,0) as maxdiff\n" +
//				"from CT_BAS_CarData a\n" +
//				"left join\n" +
//				"(\n" +
//				"  select a.CFCARDATAID as carid,\n" +
//				"         b.FNAME_L2,\n" +
//				"         max(abs(a.CFTARE-b.CFBIMUDF0001))  as  maxdiff\n" +
//				"  from CT_PRO_PoundCheck a\n" +
//				"  inner join CT_BAS_CarData b on a.CFCARDATAID = b.fid\n" +
//				"  where a.FBILLSTATUS=30\n" +
//				"  group by a.CFCARDATAID,b.FNAME_L2\n" +
//				")c on c.carid = a.fid\n" +
//				"where a.CFBillStatus = 40 and a.fname_l2 like '%" + BybUtils.parseSqlParam(keyword) + "%' " +
//				"  and a.CFBIMUDF0002 <> 1 ";
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//		while (rs.next())
//		{
//			JSONObject obj = new JSONObject();
//			obj.put("carData", rs.getString("fid"));
//			obj.put("carNumber", rs.getString("fname_l2"));
//			obj.put("saveTare", rs.getBigDecimal("skin"));
//			obj.put("maxdiff", rs.getBigDecimal("maxdiff"));
//			ja.add(obj);
//		}
//
//
//		resultJson.put("data", ja);
//		return resultJson.toString();
//	}
//
//	/**
//	 * 查询销售品种信息
//	 * @throws SQLException
//	 * @throws BOSException
//	 * @throws EASBizException
//	 */
//	public static String querySaleVarieties(Context ctx, JSONObject json) throws SQLException, EASBizException, BOSException
//	{
//		JSONObject resultJson = new JSONObject();
//		JSONObject temp1 = new JSONObject();
//		resultJson.put("code", "0000");
//		resultJson.put("msg", "查询成功");
//		JSONArray ja = new JSONArray();
//		JSONObject jsons = json.getJSONObject("data");
//
//		String shippmentId = jsons.getString("shipmentID");
//
//		SaleShipmentDetailInfo saleshipInfo = SaleShipmentDetailFactory.getLocalInstance(ctx).getSaleShipmentDetailInfo(new ObjectUuidPK(shippmentId));
//		SaleShipmentInfo shipmentInfo = SaleShipmentFactory.getLocalInstance(ctx).getSaleShipmentInfo(new ObjectUuidPK(saleshipInfo.getParent().getId()));
//		IQuotePriceArea IquoteArea =  QuotePriceAreaFactory.getLocalInstance(ctx);
//		QuotePriceAreaInfo quoteAreaInfo = null;
//		QuotePriceAreaCollection quoteAreaCol = null;
//		String quoteAreaId = null;
//		String serviceId = null ;
//		String companyId = null ;
//
//		/*************通过销售通知单的服务部获取牌价区域**********************/
//		if(shipmentInfo.getServiceOrg()!=null)
//		{
//			serviceId =shipmentInfo.getServiceOrg().getId().toString();
//
//			StringBuffer sql = new StringBuffer();
//			sql.setLength(0);
//			sql.append("/*dialect*/ ");
//			sql.append("select t1.fid ");
//			sql.append("  from T_ZB_SDBD_QuotePriceAreaServ t ");
//			sql.append("inner join T_ZB_SDBD_QuotePriceArea t1 on t1.fid = t.fparentid ");
//			sql.append("where t.fserviceorgid = '"+serviceId+"' ");
//			sql.append("  AND t1.fusedstatus = 1 ");
//			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//			while(rs.next())
//			{
//				String fid = rs.getString("fid");
//				quoteAreaCol = IquoteArea.getQuotePriceAreaCollection("where id = '"+fid+"' ");
//			}
//		}
//		/*************通过销售通知单的分公司获取牌价区域**********************/
//		if(shipmentInfo.getSubCompany()!=null && quoteAreaCol==null)
//		{
//			companyId = shipmentInfo.getSubCompany().getId().toString();
//
//			StringBuffer sql = new StringBuffer();
//			sql.setLength(0);
//			sql.append("/*dialect*/ ");
//			sql.append("select t1.fid ");
//			sql.append("  from T_ZB_SDBD_QuotePriceAreaComp t ");
//			sql.append("inner join T_ZB_SDBD_QuotePriceArea t1 on t1.fid = t.fparentid ");
//			sql.append("where t.FManOrgID = '"+companyId+"' ");
//			sql.append("  AND t1.fusedstatus = 1 ");
//			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//			while(rs.next())
//			{
//				String fid = rs.getString("fid");
//				quoteAreaCol = IquoteArea.getQuotePriceAreaCollection("where id = '"+fid+"' ");
//			}
//		}
//		if(shipmentInfo.getBizOrgUnit()!=null && quoteAreaCol==null )
//		{
//			quoteAreaCol = IquoteArea.getQuotePriceAreaCollection("where manOrg = '"+shipmentInfo.getBizOrgUnit().getId()+"' and usedStatus = '1'");
//		}
//		if(quoteAreaCol!=null && quoteAreaCol.size()>0)
//		{
//			quoteAreaInfo = quoteAreaCol.get(0);
//			quoteAreaId = quoteAreaInfo.getId().toString();
//		}
//		else
//		{
//			resultJson.put("code", "1105");
//			resultJson.put("msg", "未找到牌价区域");
//			return resultJson.toString();
//		}
//
//		StringBuffer sql = new StringBuffer();
//		sql.setLength(0);
//		sql.append("/*dialect*/ ");
//		sql.append("select ");
//		sql.append("p.fname_l2 as zpname, ");
//		sql.append("q.fname_l2 as cpname, ");
//		sql.append("p.fid as zpid,");
//		sql.append("q.fid as cpid,");
//		sql.append("aa.zpprice, ");
//		sql.append("m.FADJVALUE, ");
//		sql.append("Round(case when m.FADJTYPE = 1 then aa.zpprice*m.FADJVALUE ");
//		sql.append("     	   when m.FADJTYPE = 2 then aa.zpprice-m.FADJVALUE ");
//		sql.append("     	   when m.FADJTYPE = 0 then aa.zpprice+m.FADJVALUE end,2) as cpprice, ");
//		sql.append("m.FADJTYPE, ");
//		sql.append("g.FMIN, ");
//		sql.append("g.FMAX, ");
//		sql.append("g.FNAME_L2 ");
//		sql.append("from ");
//		sql.append("(select ");
//		sql.append("    b.fid, ");
//		sql.append("    d.FSALEPRODUCTID, ");
//		sql.append("    d.FPRICE as cpprice, ");
//		sql.append("    e.FNAME_L2 as weightqj, ");
//		sql.append("    e.FMIN, ");
//		sql.append("    e.FMAX, ");
//		sql.append("    case when c.FADJTYPE = 1 then d.FPRICE/c.FADJVALUE ");
//		sql.append("         when c.FADJTYPE = 2 then d.FPRICE+c.FADJVALUE ");
//		sql.append("         when c.FADJTYPE = 0 then d.FPRICE-c.FADJVALUE end as zpprice, ");
//		sql.append("    c.FADJVALUE, ");
//		sql.append("    c.FADJTYPE, ");
//		sql.append("    case when d.FPRODUCTSTANDARDID = e.fid then 1 else 0 end as countvalue ");
//		sql.append("from T_ZB_SDBD_QuotePriceArea a ");
//		sql.append("inner join T_ZB_SDBD_PriceFactor b on b.FQUOTEPRICEAREAID = a.fid and b.FUsedStatus =1");
//		sql.append("inner join T_ZSB_PriceFactorFactorDetail c on c.FPARENTID = b.fid ");
//		sql.append("inner join T_ZB_SD_SALESHIPMENTDETAIL d on d.fsaleproductid=c.FADJPRODUCTID ");
//		sql.append("inner join T_ZB_SDBD_ProductStandard e on e.fid =c.FWEIGHTRANGEID ");
//		sql.append("where a.fid = '"+quoteAreaId+"' ");
//		sql.append("  and d.fid = '"+shippmentId+"' ");
//		sql.append(")aa ");
//		sql.append("inner join T_ZSB_PriceFactorFactorDetail m on m.FPARENTID = aa.fid ");
//		sql.append("inner join T_ZB_SDBD_ProductStandard g on g.fid =m.FWEIGHTRANGEID ");
//		sql.append("inner join CT_BAS_PigSaleVarieties p on p.fid = m.fbaseproductid ");
//		sql.append("inner join CT_BAS_PigSaleVarieties q on q.fid = m.fadjproductid ");
//		sql.append("where m.FADJPRODUCTID = aa.FSALEPRODUCTID ");
//		sql.append("  and aa.countvalue = 1 ");
//		sql.append("order by zpprice,fmin ");
//
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//		while(rs.next())
//		{
//			JSONObject temp = new JSONObject();
//			temp1.put("zpname", rs.getString("zpname"));
//			temp1.put("zpid", rs.getString("zpid"));
//			temp1.put("cpid", rs.getString("cpid"));
//			temp1.put("cpname", rs.getString("cpname"));
//			temp1.put("zpprice", rs.getString("zpprice"));
//			temp.put("min", rs.getString("FMIN"));
//			temp.put("max", rs.getString("FMAX"));
//			temp.put("cpprice", rs.getString("cpprice"));
//			ja.add(temp);
//		}
//
//
//		temp1.put("cplist",ja );
//		resultJson.put("data", temp1);
//		return resultJson.toString();
//	}
//
//}
