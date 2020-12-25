//package com.kingdee.eas.zbsd.baseframework.app.handlers;
//
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.Context;
//import com.kingdee.bos.dao.IObjectPK;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.dao.query.SQLExecutorFactory;
//import com.kingdee.bos.json.JSONArray;
//import com.kingdee.bos.json.JSONException;
//import com.kingdee.bos.json.JSONObject;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.ui.face.UIRuleUtil;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.bos.workflow.ProcessInstInfo;
//import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
//import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
//import com.kingdee.eas.base.permission.UserFactory;
//import com.kingdee.eas.base.permission.UserInfo;
//import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
//import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
//import com.kingdee.eas.basedata.person.PersonInfo;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.custbaseframework.ToolBoxFacadeFactory;
//import com.kingdee.eas.custom.basefile.IManageOrg;
//import com.kingdee.eas.custom.basefile.ManageOrgCollection;
//import com.kingdee.eas.custom.basefile.ManageOrgFactory;
//import com.kingdee.eas.custom.basefile.ManageOrgInfo;
//import com.kingdee.eas.custom.basefile.ManageOrgUnitLevel;
//import com.kingdee.eas.custom.basefile.PigSaleVarietiesInfo;
//import com.kingdee.eas.custom.basefile.ServiceFileSetUpInfo;
//import com.kingdee.eas.custom.pig.base.PigArchivesInfo;
//import com.kingdee.eas.custom.pig.pigfarm.PigBatchInfo;
//import com.kingdee.eas.custom.producebusiness.YZ_Servicesales_handoverEntryFactory;
//import com.kingdee.eas.custom.producebusiness.YZ_Servicesales_handoverEntryInfo;
//import com.kingdee.eas.custom.producebusiness.YZ_Servicesales_handoverFactory;
//import com.kingdee.eas.custom.producebusiness.YZ_Servicesales_handoverInfo;
//import com.kingdee.eas.custom.producebusiness.Yz_rh_batchInfo;
//import com.kingdee.eas.util.app.ContextUtil;
//import com.kingdee.eas.util.app.DbUtil;
//import com.kingdee.eas.zbsd.baseframework.BillStatusEnum;
//import com.kingdee.eas.zbsd.baseframework.OrgUnitTypeEnum;
//import com.kingdee.eas.zbsd.baseframework.PriceUnitEnum;
//import com.kingdee.eas.zbsd.baseframework.SaleClassification;
//import com.kingdee.eas.zbsd.baseframework.ZBException;
//import com.kingdee.eas.zbsd.baseframework.util.DateHelper;
//import com.kingdee.eas.zbsd.baseframework.util.StringHelper;
//import com.kingdee.eas.zbsd.saleallocation.AllocatSourceInfo;
//import com.kingdee.eas.zbsd.saleallocation.ISaleAdvanceBill;
//import com.kingdee.eas.zbsd.saleallocation.SaleAdvanceBillDetailInfo;
//import com.kingdee.eas.zbsd.saleallocation.SaleAdvanceBillFactory;
//import com.kingdee.eas.zbsd.saleallocation.SaleAdvanceBillInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyDetailCollection;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyDetailInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.ProductStandardInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.ISaleCustomer;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceDetailFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceDetailInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleCustomerCollection;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleCustomerFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.SaleCustomerInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.app.webservice.WSResult;
//import com.kingdee.eas.zbsd.zbsdbizbill.app.webservice.handler.BaseHandler;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.StringUtils;
//import com.kingdee.util.UuidException;
//
//public class CreateSaleAdvanceBillHandler extends BaseHandler {
//	private static Logger logger = Logger.getLogger("com.kingdee.eas.zbsd.baseframework.app.handlers.CreateSaleAdvanceBillHandler");
//
//	public CreateSaleAdvanceBillHandler(Context ctx) {
//		super(ctx);
//	}
//
//	@Override
//		logger.error("生成竞价平台销售预订单：" + param.toString());
//		JSONObject json = null;
//		WSResult result = new WSResult();
//		boolean b = false;
//		String msg = "没有提示";
//		try {
//			String strHandoverID = json.getString("HandoverID");// 上市计划ID
//			logger.error("生成竞价平台销售预订单1、获取上市计划" + strHandoverID.toString());
//			YZ_Servicesales_handoverEntryInfo entryInfo = YZ_Servicesales_handoverEntryFactory.getLocalInstance(ctx).getYZ_Servicesales_handoverEntryInfo(new ObjectUuidPK(strHandoverID));
//			JSONArray objEntries = json.getJSONArray("Entries");// 中标明细
//			int canAllocat = entryInfo.getCount() - entryInfo.getYetAllocatQty();// 可分配数量
//			int needAllocat = 0;// 需要分配的总数
//			for (int i = 0; i < objEntries.length(); i++) {
//				JSONObject temp = objEntries.getJSONObject(i);
//				needAllocat += temp.getInt("Qty");
//			}
//			if (canAllocat >= needAllocat) {
//				BigDecimal counterpoise = entryInfo.getAvgWeight();
//				if(counterpoise == null){
//					counterpoise = BigDecimal.ZERO;
//				}
//				UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
//				if (json.has("userId")) {
//					SelectorItemCollection sic = new SelectorItemCollection();
//					sic.add(new SelectorItemInfo("*"));
//					sic.add(new SelectorItemInfo("Person.*"));
//					userInfo = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(json.getString("userId")), sic);
//				}
//				YZ_Servicesales_handoverInfo handoverInfo = getHandoverInfo(entryInfo.getParent().getId().toString());
//				PersonInfo salesMan = new PersonInfo();// 销售员Info
//				salesMan.setId(userInfo.getPerson().getId());
//				ISaleAdvanceBill saleAdvanceBillDao = SaleAdvanceBillFactory.getLocalInstance(ctx);
////					SaleAdvanceBillInfo SaleAdvanceBillInfo = createSaleAdvanceBillInfo(handoverInfo, salesMan, objEntries.getJSONObject(i), entryInfo.getSalesMix().getId().toString(), counterpoise);// 生成销售预订单
//				SaleAdvanceBillInfo saleAdvanceBillInfo = createSaleAdvanceBillInfo(handoverInfo, salesMan, objEntries, entryInfo.getSalesMix().getId().toString(), counterpoise,strHandoverID);// 生成销售预订单
//				IObjectPK pk = saleAdvanceBillDao.addnew(saleAdvanceBillInfo);// 保存销售预订单
//				logger.error("生成竞价平台销售预订单1、生成销售预订单，订单ID:" + pk.toString());
//				//by 2019年5月20日 14:28:52 yaoweigang  增加销售预订单进入流程
//				saleAdvanceBillDao.submit(saleAdvanceBillInfo);//提交销售预订单
////				if(!inFlowWork(ctx,pk.toString())){//校验是否已经在工作流中，不在工作流中则代码触发
////					logger.error("单据ID："+pk.toString()+" 代码触发工作流开始");
////					WfFacadeFactory.getLocalInstance(ctx).createWF(saleAdvanceBillInfo);
////					logger.error("单据ID："+pk.toString()+" 代码触发工作流结束");
////				}
////					List<AllocatSourceInfo> selectAllocatSources = new ArrayList<AllocatSourceInfo>();// 创建分配明细
////					AllocatSourceInfo allocatSourceInfo = getAllocatSourceInfo(entryInfo, objEntries.getJSONObject(i));
////					selectAllocatSources.add(allocatSourceInfo);
////					saleAllocatDetailDao.allocaing(selectAllocatSources, SaleAdvanceBillInfo.getDetail().get(0).getId());// 分配
//				//					SaleAdvanceBillDao.passAudit(pk, SaleAdvanceBillInfo);// 审核销售预订单
//				//end 2019年5月20日 14:29:12
//
//
//				b = true;
//				msg = "创建销售预订单成功";
//
//			} else {
//				throw new ZBException(ZBException.CUSTOM, new Object[] { "可分配数量小于需要分配的数量!" });
//			}
//
//		} catch (EASBizException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "EASBizException:" + e.getMessage();
//			e.printStackTrace();
//		} catch (BOSException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "BOSException,错误信息:" + e.getMessage();
//			e.printStackTrace();
//		} catch (UuidException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "UuidException:" + e.getMessage();
//			e.printStackTrace();
//		} catch (JSONException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "JSONException:" + e.getMessage();
//			e.printStackTrace();
//		} catch (ParseException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "ParseException:" + e.getMessage();
//			e.printStackTrace();
//		} catch (SQLException e) {
//			logger.error("生成销售预订单失败:" + e.getMessage());
//			msg = "SQLException:" + e.getMessage();
//			e.printStackTrace();
//		}
//
//		result.setSuccess(b);
//		result.setMsg(msg);
//		return result;
//	}
//
//	/**
//	 *判断单据流程运行中或挂起。
//	 *@param 单据id
//	 */
//	public boolean inFlowWork(Context ctx,String billId) throws BOSException, EASBizException{
//		boolean inFlowWork = false ;
//		if(!StringUtils.isEmpty(billId)){
//			IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
//			ProcessInstInfo instInfo = null;
//			ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(billId);
//			for (int j=0,n = procInsts.length; j < n; ++j) {
//				if ("open.running".equals(procInsts[j].getState()) || "open.not_running.suspended".equals(procInsts[j].getState())) {
//					instInfo = procInsts[j];
//				}
//			}
//			if (instInfo != null) {
//				inFlowWork = true;
//			}
//		}
//		return inFlowWork;
//	}
//
//	/****
//	 * 描述：创建销售预订单
//	 *
//	 * @param strHandoverID
//	 * @param strSalesManID
//	 * @param entries
//	 * @return
//	 * @throws EASBizException
//	 * @throws BOSException
//	 * @throws ParseException
//	 * @throws SQLException
//	 * @throws UuidException
//	 * @throws JSONException
//	 */
//	private SaleAdvanceBillInfo createSaleAdvanceBillInfo(YZ_Servicesales_handoverInfo handoverInfo, PersonInfo salesMan, JSONArray entrys, String saleProductID, BigDecimal counterpoise,String strHandoverID) throws EASBizException, BOSException, ParseException, UuidException, SQLException, JSONException {
//
//		BigDecimal counterpoise_bak = new BigDecimal("0");
//		counterpoise_bak = counterpoise;
//
//		SaleAdvanceBillInfo saleAdvanceBillInfo = new SaleAdvanceBillInfo();
//		String number = ToolBoxFacadeFactory.getLocalInstance(ctx).getCodeNumber(saleAdvanceBillInfo, ContextUtil.getCurrentCtrlUnit(ctx));
//		StorageOrgUnitInfo storageOrgUnitInfo = handoverInfo.getStorageOrgUnit();
//		AdminOrgUnitInfo adminOrgUnitInfo = new AdminOrgUnitInfo();
//		adminOrgUnitInfo.setId(storageOrgUnitInfo.getId());
//		saleAdvanceBillInfo.setNumber(number);// 编号
//		saleAdvanceBillInfo.setBillStatus(BillStatusEnum.SAVE);
//		saleAdvanceBillInfo.setBizOrgUnit(adminOrgUnitInfo);
//		saleAdvanceBillInfo.setSubCompany(getSubCompany(storageOrgUnitInfo));
//		saleAdvanceBillInfo.setSalesMan(salesMan);
//
//		saleAdvanceBillInfo.setBizDate(new Date());
//
//		BigDecimal totalMoney= new BigDecimal("0");
//
//		for(int i=0;i<entrys.length();i++){
//			JSONObject entry=entrys.getJSONObject(i);
//			SaleAdvanceBillDetailInfo saleAdvanceBillDetailInfo = new SaleAdvanceBillDetailInfo();
//
//			Object objCustomerID = entry.get("idNumber");// 客户ID
//			String idnumber = objCustomerID.toString();
//			Object objQty = entry.get("Qty");// 数量
//			BigDecimal qty = objQty == null ? BigDecimal.ZERO : new BigDecimal(objQty.toString());
//			Object objPrice = entry.get("Price");// 单价
//			BigDecimal price = objPrice == null ? BigDecimal.ZERO : new BigDecimal(objPrice.toString());
//
//			Object obj_pigSaleType = entry.get("pigSaleType");
//			Integer pigSaleType = Integer.valueOf(obj_pigSaleType.toString());
//
//			SaleCustomerInfo saleCustomerInfo = getSaleCustomerInfo(idnumber);
//			if (saleCustomerInfo == null) {
//				logger.error("客户：" + idnumber + ",不存在!");
//				throw new ZBException(ZBException.CUSTOM, new Object[] { "客户：" + idnumber + ",不存在!" });
//			}
//			saleAdvanceBillDetailInfo.setCust(saleCustomerInfo);
//
//			Object objtime = entry.get("lztime"); //预计拉猪时间
//			if (objtime != null) {
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//				Date lztime = objtime == null ? null : dateFormat.parse(objtime.toString());
//				saleAdvanceBillInfo.setDeliverTime(new Time(lztime.getTime())); //预计拉猪时间
//				saleAdvanceBillInfo.setDeliverDate(lztime); //预计拉猪时间
//			}
//
//			BigDecimal money = price.multiply(qty);
//
//
//			ProductStandardInfo productStandardInfo = null;
//			saleAdvanceBillDetailInfo.setSeq(1);
//			saleAdvanceBillDetailInfo.setSaleProduct(getPigSaleVarieties(saleProductID));
//			saleAdvanceBillDetailInfo.setPrice(price);
//			if (pigSaleType == 1) {//种猪=0,仔猪=1,肥猪=2,淘汰生产种猪=3,二级猪=4,白条=5
//				saleAdvanceBillDetailInfo.setPriceTypeUnit(PriceUnitEnum.TOU);
//				saleAdvanceBillDetailInfo.setQty(qty);
//				saleAdvanceBillDetailInfo.setBasePrice(price);
//				int comparisonValue = 0;
//				if (counterpoise.intValue() * 2 >= 20) {
//					comparisonValue = 38;
//				} else {
//					comparisonValue = 19;
//				}
//				productStandardInfo = getProductStandardInfoByCounterpoise(comparisonValue);
//				if (productStandardInfo==null ||productStandardInfo.getId()==null) {
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到规格型号!" });
//				}
//				saleAdvanceBillDetailInfo.setProductStandard(productStandardInfo);
//
//
//				saleAdvanceBillDetailInfo.setAvgWeight(counterpoise.multiply(BigDecimal.valueOf(2)));
//				saleAdvanceBillDetailInfo.setPricePer(price);
//				PricePolicyDetailCollection pigLetPricePolicys = QuotePriceFactory.getLocalInstance(ctx).getPricePolicyDetailBySaleClassification(SaleClassification.Piglet);
//				String quotePriceDetailId = getQuotePriceDetailId(saleAdvanceBillInfo.getBizDate(), storageOrgUnitInfo.getId().toString(), saleProductID, productStandardInfo.getId().toString());
//				if (StringHelper.isEmpty(quotePriceDetailId)) {
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到牌价!" });
//				}
//				QuotePriceDetailInfo quotePriceDetailInfo = QuotePriceDetailFactory.getLocalInstance(ctx).getQuotePriceDetailInfo(new ObjectUuidPK(quotePriceDetailId));
//				if (quotePriceDetailInfo == null) {
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到近一个月牌价!" });
//				}
//				setZZPrice(saleAdvanceBillDetailInfo, pigLetPricePolicys, quotePriceDetailInfo, price);
//				calMoney(saleAdvanceBillDetailInfo);
//
//			} else {
//
//				productStandardInfo = getProductStandardInfoByCounterpoise(counterpoise.intValue()*2);
//				if (productStandardInfo==null ||productStandardInfo.getId()==null) {
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到规格型号!" });
//				}
//				saleAdvanceBillDetailInfo.setPriceTypeUnit(PriceUnitEnum.KG);
//				saleAdvanceBillDetailInfo.setQty(qty);
//
//				/******modify tanrongtao 新增偏差价格  start*********/
//				//获取牌价区域
//				BigDecimal quotePrice = BigDecimal.ZERO;
//				String priceAreaId = getPriceAreaId(storageOrgUnitInfo);
//				if(!"".equals(priceAreaId)){
//					//获取最近一次牌价
//					quotePrice = QuotePriceFactory.getLocalInstance(ctx).getPriceLastFZ(priceAreaId, saleProductID, productStandardInfo.getId().toString());
//				}else{
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到牌价区域!" });
//				}
//				if( UIRuleUtil.isNotNull(price)){
//					if(quotePrice.compareTo(BigDecimal.ZERO) != 0){
//						saleAdvanceBillDetailInfo.setQuotePrice(quotePrice);                         //牌价
//						saleAdvanceBillDetailInfo.setDeviationPrice(price.subtract(quotePrice));     //偏差价格 = 单价 - 牌价
//					}else{
//						throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到牌价!" });
//					}
//				}else{
//					throw new ZBException(ZBException.CUSTOM, new Object[] { "未获取到牌价!" });
//				}
//				/******modify tanrongtao 新增偏差价格  end*********/
//
//				if (counterpoise != null) {
//					counterpoise = counterpoise.multiply(new BigDecimal(2));
//					saleAdvanceBillDetailInfo.setAvgWeight(counterpoise);
//					money = money.multiply(counterpoise);
//					saleAdvanceBillDetailInfo.setProductStandard(productStandardInfo);// 规格
//					counterpoise = counterpoise_bak;
//				}
//				saleAdvanceBillDetailInfo.setMoney(money);
//
//			}
//
//			totalMoney = totalMoney.add(saleAdvanceBillDetailInfo.getMoney());
//
//			saleAdvanceBillDetailInfo.setSourceBillId(entry.getString("bidId"));// 标的ID（竞价平台）
//			saleAdvanceBillDetailInfo.setSourceFunction("jjpt");
//
//			saleAdvanceBillInfo.getDetail().add(saleAdvanceBillDetailInfo);
//
//			saleAdvanceBillInfo.setSourceFunction("jjpt");
//
//		}
//
//		saleAdvanceBillInfo.setNmny(totalMoney);
//		saleAdvanceBillInfo.setHandoverId(strHandoverID);//上市计划ID
//		return saleAdvanceBillInfo;
//	}
//
//
//	private String getPriceAreaId(StorageOrgUnitInfo storageOrgUnitInfo) throws BOSException, SQLException {
//		String priceAreaId = "";
//		StringBuffer sql = new StringBuffer();
//		sql.append("/*dialect*/ \n");
//		sql.append("SELECT DISTINCT FID \n");
//		sql.append("  FROM T_ZB_SDBD_QUOTEPRICEAREA A \n");
//		sql.append(" WHERE FUSEDSTATUS = 1 \n");
//		sql.append("   AND (A.FID IN \n");
//		sql.append("       (SELECT FPARENTID \n");
//		sql.append("           FROM T_ZB_SDBD_QUOTEPRICEAREASERV \n");
//		sql.append("          WHERE FSERVICEORGID IN \n");
//		sql.append("                (SELECT FID \n");
//		sql.append("                   FROM CT_BAS_SERVICEFILESETUP \n");
//		sql.append("                  WHERE CFSTORAGEORGUNITID = '" + storageOrgUnitInfo.getId().toString() + "')) OR \n");
//		sql.append("       A.FID IN (SELECT A.FPARENTID \n");
//		sql.append("                    FROM T_ZB_SDBD_QUOTEPRICEAREAPIGPEN A \n");
//		sql.append("                   INNER JOIN CT_BAS_PIGARCHIVES B \n");
//		sql.append("                      ON A.FPIGPENID = B.FID \n");
//		sql.append("                   WHERE B.CFORGIDID = '" + storageOrgUnitInfo.getId().toString() + "')) \n");
//
//		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
//		while(rs.next()){
//			priceAreaId = rs.getString("FID");
//		}
//		return priceAreaId;
//	}
//
//	private void calMoney(SaleAdvanceBillDetailInfo saleAdvanceBillDetailInfo) {
//		BigDecimal price = saleAdvanceBillDetailInfo.getPrice();
//		BigDecimal qty = saleAdvanceBillDetailInfo.getQty();
//		BigDecimal avgWeight = saleAdvanceBillDetailInfo.getAvgWeight();
//		BigDecimal baseWeight = saleAdvanceBillDetailInfo.getBaseWeight();
//		if(baseWeight==null){
//			baseWeight = BigDecimal.ZERO;
//		}
//
//		baseWeight = baseWeight.multiply(BigDecimal.valueOf(2));
//		BigDecimal exceedWeight = avgWeight.subtract(baseWeight);
//		BigDecimal amountPer = price.multiply(qty);
//		BigDecimal exceedAmount = BigDecimal.ZERO;
//		BigDecimal money = BigDecimal.ZERO;
//		if (exceedWeight.compareTo(BigDecimal.ZERO) >= 0) {
//			BigDecimal weightMorePrice = saleAdvanceBillDetailInfo.getWeightMorePrice();
//			weightMorePrice = weightMorePrice == null ? BigDecimal.ZERO : weightMorePrice;
//			exceedWeight = exceedWeight.abs();
//			exceedAmount = weightMorePrice.multiply(exceedWeight).multiply(qty);
//			money = amountPer.add(exceedAmount);
//
//		} else {
//			BigDecimal weightLessPrice = saleAdvanceBillDetailInfo.getWeightLessPrice();
//			weightLessPrice = weightLessPrice == null ? BigDecimal.ZERO : weightLessPrice;
//			exceedWeight = exceedWeight.abs();
//			exceedAmount = weightLessPrice.multiply(exceedWeight).multiply(qty);
//			money = amountPer.subtract(exceedAmount);
//		}
//
//		saleAdvanceBillDetailInfo.setMoney(money);
//
//	}
//
//	private String getQuotePriceDetailId(Date bizData, String storageOrgUnitID, String saleProductID, String productStandardID) throws ParseException, BOSException, SQLException {
//		String endDate = DateHelper.getDTStyle4(bizData);
//		String stratDate = DateHelper.getDTStyle4(DateHelper.getNextDate(bizData, -30, Calendar.DATE, null));
//		StringBuffer sql = new StringBuffer();
//		sql.append(" /*dialect*/ ");
//		sql.append(" select  a.fid QuotePriceDetailId,  a.* ");
//		sql.append("     from  T_ZB_SD_QuotePriceDetail  a ");
//		sql.append("   inner  join  T_ZB_SDBD_QuotePriceArea  b ");
//		sql.append("         on  a.fpriceareaid  =  b.fid ");
//		sql.append("   inner  join  T_ZB_SDBD_ProductStandard  c ");
//		sql.append("         on  c.fid  =  a.fproductstandardid ");
//		sql.append("   inner  join  T_ZB_SD_QuotePrice  d ");
//		sql.append("         on  d.fid  =  a.fparentid ");
//		sql.append("   where  d.fbillstatus  =  10 ");
//		sql.append("       and  a.FPriceAreaID  in  ( ");
//		sql.append("                                                     select  DISTINCT  d.fid  quotePriceAreaId ");
//		sql.append("                                                         from  T_ZB_SDBD_QuotePriceAreaPigPen  a ");
//		sql.append("                                                       inner  join  CT_BAS_PigArchives  b ");
//		sql.append("                                                             on  a.FPigPenID  =  b.fid ");
//		sql.append("                                                       inner  join  T_ORG_Storage  c ");
//		sql.append("                                                             on  c.fid  =  b.CFOrgidID ");
//		sql.append("                                                       inner  join  T_ZB_SDBD_QuotePriceArea  d ");
//		sql.append("                                                             on  d.fid  =  a.fparentid ");
//		sql.append("                                                       inner  join  CT_BAS_FieldFile  e ");
//		sql.append("                                                             on  e.CFPigfarmID  =  b.fid ");
//		sql.append("                                                       where  c.fid  =  '" + storageOrgUnitID + "' ");
//		sql.append("                                                             or  e.CFStockorgID  =  '" + storageOrgUnitID + "' ");
//		sql.append("                                                           and  b.CFDeletedStatus  =  1 ");
//		sql.append("                                                           and  (b.Cfdr  =  0  or  b.Cfdr  is  null) ");
//		sql.append("                                                           and  d.Fusedstatus  =  1) ");
//		sql.append("       and  a.FSaleProductID  =  '" + saleProductID + "' ");
//		sql.append("       and  a.FProductStandardID  =  '" + productStandardID + "' ");
//		sql.append("       and  to_char(d.fbizdate,  'yyyy-mm-dd')  >=  '" + stratDate + "' ");
//		sql.append("       and  to_char(d.fbizdate,  'yyyy-mm-dd')  <=  '" + endDate + "' ");
//		sql.append("       and  a.fprice1  >  0 ");
//		sql.append("   order  by  d.fstartdate  desc ");
//
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//		if (rs.next()) {
//			return rs.getString("QuotePriceDetailId");
//		}
//		return null;
//
//	}
//
//	/**
//	 * 设置种、仔猪基础价、减重价、超重价、基础重
//	 * @param ppds
//	 * @param price2
//	 * @param price1
//	 * @param price2
//	 * @param price3
//	 */
//	private void setZZPrice(SaleAdvanceBillDetailInfo saleAdvanceBillDetailInfo, PricePolicyDetailCollection ppds, QuotePriceDetailInfo quotePriceDetailInfo, BigDecimal price2) {
//		for (int i = 0; i < ppds.size(); i++) {
//			PricePolicyDetailInfo policyDetailInfo = ppds.get(i);
//			BigDecimal price = quotePriceDetailInfo.getBigDecimal(policyDetailInfo.getPriceItemCode());
//			if (policyDetailInfo.getName().contains("基础价")) {
//				saleAdvanceBillDetailInfo.setQuotePrice(price);                          //牌价
//				saleAdvanceBillDetailInfo.setDeviationPrice(price2.subtract(price));     //偏差价格 = 单价 - 牌价
//			} else if (policyDetailInfo.getName().contains("减重价")) {
//				saleAdvanceBillDetailInfo.setWeightLessPrice(price);
//			} else if (policyDetailInfo.getName().contains("超重价")) {
//				saleAdvanceBillDetailInfo.setWeightMorePrice(price);
//			} else if (policyDetailInfo.getName().contains("基础重")) {
//				saleAdvanceBillDetailInfo.setBaseWeight(price);
//			}
//		}
//	}
//
//	/****
//	 * 描述：获得上市计划
//	 *
//	 * @param strHandoverID
//	 *            上市计划ID
//	 * @return
//	 * @throws EASBizException
//	 * @throws BOSException
//	 */
//	private YZ_Servicesales_handoverInfo getHandoverInfo(String strHandoverID) throws EASBizException, BOSException {
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("storageOrgUnit.id"));
//		sic.add(new SelectorItemInfo("entrys.id"));
//		sic.add(new SelectorItemInfo("id"));
//		return YZ_Servicesales_handoverFactory.getLocalInstance(ctx).getYZ_Servicesales_handoverInfo(new ObjectUuidPK(strHandoverID), sic);
//	}
//
//	/****
//	 * 描述：获取分公司
//	 *
//	 * @param storageOrgUnitInfo
//	 * @return
//	 * @throws BOSException
//	 */
//	private ManageOrgInfo getSubCompany(StorageOrgUnitInfo storageOrgUnitInfo) throws BOSException {
//		IManageOrg iManageOrgDao = ManageOrgFactory.getLocalInstance(ctx);
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("adminOrgUnit.id", storageOrgUnitInfo.getId(), CompareType.EQUALS));
//		view.setFilter(filter);
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("id"));
//		sic.add(new SelectorItemInfo("manageOrgUnitLevel"));
//		sic.add(new SelectorItemInfo("parent.id"));
//		view.setSelector(sic);
//		ManageOrgCollection manageOrgCollection = iManageOrgDao.getManageOrgCollection(view);
//		if (manageOrgCollection != null && manageOrgCollection.size() > 0) {
//			return getSubCompany(manageOrgCollection.get(0));
//		}
//		return null;
//	}
//
//	private ManageOrgInfo getSubCompany(ManageOrgInfo manageOrgInfo) throws BOSException {
//		if (manageOrgInfo == null) {
//			return null;
//		} else if (manageOrgInfo.getManageOrgUnitLevel().equals(ManageOrgUnitLevel.branchCompany)) {
//			return manageOrgInfo;
//		} else {
//			IManageOrg iManageOrgDao = ManageOrgFactory.getLocalInstance(ctx);
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id", manageOrgInfo.getParent().getId(), CompareType.EQUALS));
//			view.setFilter(filter);
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add(new SelectorItemInfo("id"));
//			sic.add(new SelectorItemInfo("manageOrgUnitLevel"));
//			sic.add(new SelectorItemInfo("parent.id"));
//			view.setSelector(sic);
//			ManageOrgCollection manageOrgCollection = iManageOrgDao.getManageOrgCollection(view);
//			if (manageOrgCollection != null && manageOrgCollection.size() > 0) {
//				return getSubCompany(manageOrgCollection.get(0));
//			}
//		}
//		return null;
//	}
//
//	/***
//	 * 描述：获取销售客户
//	 *
//	 * @param saleCustomerID
//	 * @return
//	 * @throws EASBizException
//	 * @throws BOSException
//	 */
//	private SaleCustomerInfo getSaleCustomerInfo(String idNumber) throws EASBizException, BOSException {
//		ISaleCustomer iSaleCustomerDao = SaleCustomerFactory.getLocalInstance(ctx);
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("idNumber", idNumber, CompareType.EQUALS));
//		view.setFilter(filter);
//		SaleCustomerCollection saleCustomers = iSaleCustomerDao.getSaleCustomerCollection(view);
//		if (saleCustomers != null && saleCustomers.size() > 0) {
//			return saleCustomers.get(0);
//		} else {
//			return null;
//		}
//	}
//
//	private PigSaleVarietiesInfo getPigSaleVarieties(String saleProductID) throws BOSException, EASBizException {
//		//		IPigSaleVarieties iPigSaleVarietiesDao = PigSaleVarietiesFactory.getLocalInstance(ctx);
//		//		SelectorItemCollection sic = new SelectorItemCollection();
//		//		sic.add(new SelectorItemInfo("id"));
//		//		sic.add(new SelectorItemInfo("Car.carData.*"));
//		//		sic.add(new SelectorItemInfo("Car.*"));
//		PigSaleVarietiesInfo pigSaleVarietiesInfo = new PigSaleVarietiesInfo();
//		pigSaleVarietiesInfo.setId(BOSUuid.read(saleProductID));
//		return pigSaleVarietiesInfo;
//	}
//
//	/****
//	 * 描述：获取规格型号
//	 *
//	 * @param counterpoise
//	 * @return
//	 * @throws UuidException
//	 * @throws SQLException
//	 * @throws BOSException
//	 * @throws ZBException
//	 */
//	private ProductStandardInfo getProductStandardInfoByCounterpoise(int counterpoise) throws UuidException, SQLException, BOSException, ZBException {
//		String sql = "/*dialect*/ select fid,fname_l2,fnumber from T_ZB_SDBD_ProductStandard where nvl(fmax,99999) >=" + counterpoise + " and nvl(fmin,0.1)<=" + counterpoise;
//		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
//		ProductStandardInfo productStandardInfo = null;
//		while (rowSet.next()) {
//			if (productStandardInfo == null) {
//				productStandardInfo = new ProductStandardInfo();
//				productStandardInfo.setId(BOSUuid.read(rowSet.getString("fid")));
//				productStandardInfo.setNumber(rowSet.getString("fnumber"));
//				productStandardInfo.setName(rowSet.getString("fname_l2"));
//				return productStandardInfo;
//			} else {
//				throw new ZBException(ZBException.CUSTOM, new String[] { "无法匹配唯一的重量区间，请检查重量区间设置！" });
//			}
//		}
//		return null;
//	}
//
//	/****
//	 * 描述：根据上市计划分录生成分配信息
//	 *
//	 * @param handoverEntryInfo
//	 * @return
//	 * @throws BOSException
//	 * @throws EASBizException
//	 * @throws JSONException
//	 */
//	private AllocatSourceInfo getAllocatSourceInfo(YZ_Servicesales_handoverEntryInfo handoverEntryInfo, JSONObject entry) throws EASBizException, BOSException, JSONException {
//		Object objQty = entry.get("Qty");// 数量
//		BigDecimal qty = objQty == null ? BigDecimal.ZERO : new BigDecimal(objQty.toString());
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("id"));
//		sic.add(new SelectorItemInfo("SalesMix.name"));
//		sic.add(new SelectorItemInfo("Parent.Servicedepartment.id"));
//		sic.add(new SelectorItemInfo("Parent.Servicedepartment.ServiceName"));
//		sic.add(new SelectorItemInfo("Parent.Pigfarm.id"));
//		sic.add(new SelectorItemInfo("Parent.Pigfarm.name"));
//		sic.add(new SelectorItemInfo("Count"));
//		sic.add(new SelectorItemInfo("YetAllocatQty"));
//		sic.add(new SelectorItemInfo("SeasonDate"));
//		sic.add(new SelectorItemInfo("AvgWeight"));
//		sic.add(new SelectorItemInfo("BatchNo.id"));
//		sic.add(new SelectorItemInfo("BatchNo.BatchName"));
//		sic.add(new SelectorItemInfo("PigBatch.id"));
//		sic.add(new SelectorItemInfo("PigBatch.Number"));
//		handoverEntryInfo = YZ_Servicesales_handoverEntryFactory.getLocalInstance(ctx).getYZ_Servicesales_handoverEntryInfo(new ObjectUuidPK(handoverEntryInfo.getId()), sic);
//		YZ_Servicesales_handoverInfo handoverInfo = handoverEntryInfo.getParent();
//		BigDecimal big_sumQty = new BigDecimal(handoverEntryInfo.getCount());
//		BigDecimal big_yetAllocatQty = new BigDecimal(handoverEntryInfo.getYetAllocatQty());
//		BigDecimal big_canAllocatQty = big_sumQty.subtract(big_yetAllocatQty);
//		if (big_canAllocatQty.compareTo(BigDecimal.ZERO) >= 0) {
//			AllocatSourceInfo allocatSourceInfo = new AllocatSourceInfo();
//			allocatSourceInfo.setHandoverEntryId(handoverEntryInfo.getId());
//			allocatSourceInfo.setSaleVariety(handoverEntryInfo.getSalesMix().getName());
//			allocatSourceInfo.setCanAllocatQty(big_canAllocatQty);
//			allocatSourceInfo.setYetAllocatQty(big_yetAllocatQty);
//			allocatSourceInfo.setBatchSumQty(big_sumQty);
//			allocatSourceInfo.setMarketDays(new BigDecimal(handoverEntryInfo.getSeasonDate()));
//			allocatSourceInfo.setAvgWeight(handoverEntryInfo.getAvgWeight());
//			allocatSourceInfo.setThisAllocatQty(qty);
//			Yz_rh_batchInfo s_batchInfo = handoverEntryInfo.getBatchNo();
//			PigBatchInfo p_batchInfo = handoverEntryInfo.getPigBatch();
//			OrgUnitTypeEnum orgUnitTypeEnum = null;
//			BOSUuid orgUnitId = null, batchId = null;
//			String orgUnitName = null, batchName = null;
//			if (s_batchInfo != null) {
//				ServiceFileSetUpInfo orgInfo = handoverInfo.getServicedepartment();
//				orgUnitTypeEnum = OrgUnitTypeEnum.serviceOrg;
//				orgUnitId = orgInfo.getId();
//				batchId = s_batchInfo.getId();
//				orgUnitName = orgInfo.getServiceName();
//				batchName = s_batchInfo.getBatchName();
//
//			} else if (p_batchInfo != null) {
//				PigArchivesInfo orgInfo = handoverInfo.getPigfarm();
//				orgUnitTypeEnum = OrgUnitTypeEnum.pigFarm;
//				orgUnitId = orgInfo.getId();
//				batchId = p_batchInfo.getId();
//				orgUnitName = orgInfo.getName();
//				batchName = p_batchInfo.getNumber();
//			}
//			allocatSourceInfo.setOrgUnitType(orgUnitTypeEnum);
//			allocatSourceInfo.setOrgUnitId(orgUnitId);
//			allocatSourceInfo.setOrgUnitName(orgUnitName);
//			allocatSourceInfo.setBatchId(batchId);
//			allocatSourceInfo.setBatchName(batchName);
//			return allocatSourceInfo;
//		}
//		return null;
//	}
//
//
//	/******************************/
//	private String getListedQuotePriceDetailId(Date bizData, String storageOrgUnitID, String saleProductID, String productStandardID) throws ParseException, BOSException, SQLException {
//		String endDate = DateHelper.getDTStyle4(bizData);
//		String stratDate = DateHelper.getDTStyle4(DateHelper.getNextDate(bizData, -30, Calendar.DATE, null));
//		StringBuffer sql = new StringBuffer();
//		sql.append(" /*dialect*/ ");
//		sql.append(" select  a.fid QuotePriceDetailId,  a.* ");
//		sql.append("     from  T_ZBS_ListedQuotePriceDetail  a ");
//		sql.append("   inner  join  T_ZSB_ListedQuotePriceArea  b ");
//		sql.append("         on  a.fpriceareaid  =  b.fid ");
//		sql.append("   inner  join  T_ZSB_ListedProductSd  c ");
//		sql.append("         on  c.fid  =  a.fproductstandardid ");
//		sql.append("   inner  join  T_ZBS_ListedQuotePrice  d ");
//		sql.append("         on  d.fid  =  a.fparentid ");
//		sql.append("   where  d.fbillstatus  =  10 ");
//		sql.append("       and  a.FPriceAreaID  in  ( ");
//		sql.append("                                                     select  DISTINCT  d.fid  quotePriceAreaId ");
//		sql.append("                                                         from  T_ZSB_ListedQPAPP  a ");
//		sql.append("                                                       inner  join  CT_BAS_PigArchives  b ");
//		sql.append("                                                             on  a.FPigPenID  =  b.fid ");
//		sql.append("                                                       inner  join  T_ORG_Storage  c ");
//		sql.append("                                                             on  c.fid  =  b.CFOrgidID ");
//		sql.append("                                                       inner  join  T_ZSB_ListedQuotePriceArea  d ");
//		sql.append("                                                             on  d.fid  =  a.fparentid ");
//		sql.append("                                                       inner  join  CT_BAS_FieldFile  e ");
//		sql.append("                                                             on  e.CFPigfarmID  =  b.fid ");
//		sql.append("                                                       where  c.fid  =  '" + storageOrgUnitID + "' ");
//		sql.append("                                                             or  e.CFStockorgID  =  '" + storageOrgUnitID + "' ");
//		sql.append("                                                           and  b.CFDeletedStatus  =  1 ");
//		sql.append("                                                           and  (b.Cfdr  =  0  or  b.Cfdr  is  null) ");
//		sql.append("                                                           and  d.Fusedstatus  =  1) ");
//		sql.append("       and  a.FSaleProductID  =  '" + saleProductID + "' ");
//		sql.append("       and  a.FProductStandardID  =  '" + productStandardID + "' ");
//		sql.append("       and  to_char(d.fbizdate,  'yyyy-mm-dd')  >=  '" + stratDate + "' ");
//		sql.append("       and  to_char(d.fbizdate,  'yyyy-mm-dd')  <=  '" + endDate + "' ");
//		sql.append("       and  a.fprice1  >  0 ");
//		sql.append("   order  by  d.fstartdate  desc ");
//
//		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//		if (rs.next()) {
//			return rs.getString("QuotePriceDetailId");
//		}
//		return null;
//
//	}
////	/**
////	 * 设置种、仔猪基础价、减重价、超重价、基础重
////	 * @param ppds
////	 * @param price2
////	 * @param price1
////	 * @param price2
////	 * @param price3
////	 */
////	private void setZZPrice(SaleAdvanceBillDetailInfo SaleAdvanceBillDetailInfo, ListedPricePolicyDetailCollection ppds, ListedQuotePriceDetailInfo listedQuotePriceDetailInfo, BigDecimal price2) {
////		for (int i = 0; i < ppds.size(); i++) {
////			ListedPricePolicyDetailInfo policyDetailInfo = ppds.get(i);
////			BigDecimal price = listedQuotePriceDetailInfo.getBigDecimal(policyDetailInfo.getPriceItemCode());
////			if (policyDetailInfo.getName().contains("基础价")) {
////				SaleAdvanceBillDetailInfo.setQuotePrice(price);                          //牌价
////				SaleAdvanceBillDetailInfo.setDeviationPrice(price2.subtract(price));     //偏差价格 = 单价 - 牌价
////			} else if (policyDetailInfo.getName().contains("减重价")) {
////				SaleAdvanceBillDetailInfo.setWeightLessPrice(price);
////			} else if (policyDetailInfo.getName().contains("超重价")) {
////				SaleAdvanceBillDetailInfo.setWeightMorePrice(price);
////			} else if (policyDetailInfo.getName().contains("基础重")) {
////				SaleAdvanceBillDetailInfo.setBaseWeight(price);
////			}
////		}
////	}
//
//	//	/****
////	 * 描述：获取规格型号
////	 *
////	 * @param counterpoise
////	 * @return
////	 * @throws UuidException
////	 * @throws SQLException
////	 * @throws BOSException
////	 * @throws ZBException
////	 */
////	private ListedProductSdInfo getListedProductStandardInfoByCounterpoise(int counterpoise) throws UuidException, SQLException, BOSException, ZBException {
////		String sql = "/*dialect*/ select fid,fname_l2,fnumber from T_ZSB_ListedProductSd where nvl(fmax,99999) >=" + counterpoise + " and nvl(fmin,0.1)<=" + counterpoise;
////		IRowSet rowSet = DbUtil.executeQuery(ctx, sql);
////		ListedProductSdInfo listedProductStandardInfo = null;
////		while (rowSet.next()) {
////			if (listedProductStandardInfo == null) {
////				listedProductStandardInfo = new ListedProductSdInfo();
////				listedProductStandardInfo.setId(BOSUuid.read(rowSet.getString("fid")));
////				listedProductStandardInfo.setNumber(rowSet.getString("fnumber"));
////				listedProductStandardInfo.setName(rowSet.getString("fname_l2"));
////				return listedProductStandardInfo;
////			} else {
////				throw new ZBException(ZBException.CUSTOM, new String[] { "无法匹配唯一的重量区间，请检查重量区间设置！" });
////			}
////		}
////		return null;
////	}
//	private String getListedPriceAreaId(StorageOrgUnitInfo storageOrgUnitInfo) throws BOSException, SQLException {
//		String priceAreaId = "";
//		StringBuffer sql = new StringBuffer();
//		sql.append("/*dialect*/ \n");
//		sql.append("SELECT DISTINCT FID \n");
//		sql.append("  FROM T_ZSB_ListedQuotePriceArea A \n");
//		sql.append(" WHERE FUSEDSTATUS = 1 \n");
//		sql.append("   AND (A.FID IN \n");
//		sql.append("       (SELECT FPARENTID \n");
//		sql.append("           FROM T_ZSB_ListedQPASO \n");
//		sql.append("          WHERE FSERVICEORGID IN \n");
//		sql.append("                (SELECT FID \n");
//		sql.append("                   FROM CT_BAS_SERVICEFILESETUP \n");
//		sql.append("                  WHERE CFSTORAGEORGUNITID = '" + storageOrgUnitInfo.getId().toString() + "')) OR \n");
//		sql.append("       A.FID IN (SELECT A.FPARENTID \n");
//		sql.append("                    FROM T_ZSB_ListedQPAPP A \n");
//		sql.append("                   INNER JOIN CT_BAS_PIGARCHIVES B \n");
//		sql.append("                      ON A.FPIGPENID = B.FID \n");
//		sql.append("                   WHERE B.CFORGIDID = '" + storageOrgUnitInfo.getId().toString() + "')) \n");
//
//		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
//		while(rs.next()){
//			priceAreaId = rs.getString("FID");
//		}
//		return priceAreaId;
//	}
//}
