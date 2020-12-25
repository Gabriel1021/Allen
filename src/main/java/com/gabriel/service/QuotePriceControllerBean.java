//package com.kingdee.eas.zbsd.zbsdbizbill.app;
//
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.Context;
//import com.kingdee.bos.dao.IObjectCollection;
//import com.kingdee.bos.dao.IObjectPK;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.dao.query.SQLExecutorFactory;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.metadata.entity.SorterItemCollection;
//import com.kingdee.bos.metadata.entity.SorterItemInfo;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.ui.face.UIRuleUtil;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.zbsd.baseframework.SaleClassification;
//import com.kingdee.eas.zbsd.baseframework.UsedStatusEnum;
//import com.kingdee.eas.zbsd.baseframework.ZBException;
//import com.kingdee.eas.zbsd.baseframework.util.DateHelper;
//import com.kingdee.eas.zbsd.zbsdbasedata.IPricePolicyDetail;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyDetailCollection;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyDetailFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyDetailInfo;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyFactory;
//import com.kingdee.eas.zbsd.zbsdbasedata.PricePolicyInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.IQuotePriceDetail;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceDetailCollection;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceDetailFactory;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceDetailInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.QuotePriceInfo;
//import com.kingdee.eas.zbsd.zbsdbizbill.app.webservice.handler.SaleUtil;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.NumericExceptionSubItem;
//
//public class QuotePriceControllerBean extends AbstractQuotePriceControllerBean {
//	private static Logger logger = Logger.getLogger("com.kingdee.eas.zbsd.zbsdbizbill.app.QuotePriceControllerBean");
//
//	protected void _close(Context ctx, String id) throws BOSException, EASBizException {
//	}
//
//	protected void _open(Context ctx, String id) throws BOSException, EASBizException {
//	}
//
//	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//		model.put("startdate", model.get("bizdate"));
//		model.put("enddate", model.get("bizdate"));
//		return super._save(ctx, model);
//	}
//
//	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
//		model.put("startdate", model.get("bizdate"));
//		model.put("enddate", model.get("bizdate"));
//		super._update(ctx, pk, model);
//	}
//
//	protected IObjectCollection _getPricePolicyDetailBySaleClassification(Context ctx, SaleClassification saleClassification) throws BOSException, EASBizException {
//		EntityViewInfo view = new EntityViewInfo();
//		// 增加牌价模板查询条件
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("parent.usedStatus", UsedStatusEnum.ENABLED_VALUE, CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("parent.saleType", saleClassification.getValue(), CompareType.EQUALS));
//		view.setFilter(filter);
//
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("priceItemCode"));
//		sic.add(new SelectorItemInfo("name"));
//		sic.add(new SelectorItemInfo("unionName"));
//		sic.add(new SelectorItemInfo("saleProduct.*"));
//		sic.add(new SelectorItemInfo("productStandard.*"));
//		view.setSelector(sic);
//		SorterItemCollection sorterColl = new SorterItemCollection();
//		SorterItemInfo sorterItemInfo = new SorterItemInfo();
//		sorterItemInfo.setSortType(com.kingdee.bos.metadata.data.SortType.ASCEND);
//		sorterItemInfo.setPropertyName("seq");
//		sorterColl.add(sorterItemInfo);
//		view.setSorter(sorterColl);
//		return PricePolicyDetailFactory.getLocalInstance(ctx).getPricePolicyDetailCollection(view);
//	}
//
//	protected int _getSaleAreaGroupMaxCount(Context ctx) throws BOSException, EASBizException {
//		try {
//			String sql = "SELECT MAX(FLEVEL) MAXLEVEL FROM T_ZB_SDBD_SALEAREATREE";
//			IRowSet rs = com.kingdee.eas.util.app.DbUtil.executeQuery(ctx, sql);
//			if (rs.size() > 0) {
//				if (rs.next()) {
//					return rs.getInt("MAXLEVEL");
//				}
//			}
//		} catch (SQLException e) {
//			throw new EASBizException(new NumericExceptionSubItem("0101", e.getMessage()));
//		}
//		return 0;
//	}
//
//	@Override
//	/*
//	 * 查询产品单价
//	 */
//	protected BigDecimal _getPrice(Context ctx, String priceAreaId, String productId, Date date, String productStand) throws BOSException, EASBizException {
//		if (priceAreaId == null || priceAreaId.equals("")) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "价格区域为空！" });
//		}
//		IQuotePriceDetail iqp = QuotePriceDetailFactory.getLocalInstance(ctx);
//		String itemCode = getPriceItemFieldName(ctx, productId, null, productStand);
//		if (itemCode == null || itemCode.equals("")) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品牌价模板失败，请检查牌价模板中是否包含该产品" });
//		}
//		String oql = "select " + itemCode + " where  parent.billStatus = 10 and priceArea.id = '" + priceAreaId + "' and parent.startDate <= {" + DateHelper.getDTStyle4(date) + " 23:59:59" + "} and parent.endDate >={" + DateHelper.getDTStyle4(date) + " 00:00:00}" + " and parent.saleType ="
//				+ SaleClassification.HEAVYPIG_VALUE + " order by parent.versionNumber desc";
//		QuotePriceDetailCollection priceDetails = iqp.getQuotePriceDetailCollection(oql);
//		if (priceDetails.size() == 0) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品单价失败，请检查该价格区域的牌价" });
//		}
//		return priceDetails.get(0).getBigDecimal(itemCode);
//	}
//
//	@Override
//	protected Map<String, BigDecimal> _getPriceMap(Context ctx, String priceAreaId, Date date) throws BOSException, EASBizException {
//		IPricePolicyDetail ppd = PricePolicyDetailFactory.getLocalInstance(ctx);
//		PricePolicyDetailCollection policyDetails = ppd.getPricePolicyDetailCollection();
//		// 查询模板
//		/*
//		 * Map<String, String> fieldCodeMap = new HashMap<String, String>();
//		 * Iterator iterator = policyDetails.iterator(); while
//		 * (iterator.hasNext()) { PricePolicyDetailInfo policyDetailInfo =
//		 * (PricePolicyDetailInfo) iterator.next(); String priceFieldCode =
//		 * policyDetailInfo.getPriceItemCode(); String saleProductId =
//		 * policyDetailInfo.getSaleProduct().getId().toString();
//		 * fieldCodeMap.put(priceFieldCode, saleProductId); }
//		 */
//
//		// 查询牌价
//		Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		IQuotePriceDetail iqpd = QuotePriceDetailFactory.getLocalInstance(ctx);
//		String oql = "/*dialect*/ select a.fid from t_zb_sd_quotepricedetail a " + "inner join t_zb_sd_quoteprice b on b.fid = a.fparentid " + "where a.fpriceareaid = '" + priceAreaId + "' and to_char(b.fbizdate,'yyyy-MM-dd') = '" + sdf.format(date) + "' " + "and b.fbillstatus = 10 ";
//		IRowSet rs = SaleUtil.doSql(ctx, oql);
//		// QuotePriceDetailCollection priceDetails =
//		// iqpd.getQuotePriceDetailCollection(oql);
//		String preFix = "price";
//		try {
//			if (!rs.next()) {
//				return priceMap;
//			}
//			String strId = rs.getString("fid");
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add(new SelectorItemInfo("*"));
//			sic.add(new SelectorItemInfo("Parent.*"));
//			sic.add(new SelectorItemInfo("Parent.priceTemp.*"));
//			sic.add(new SelectorItemInfo("Parent.priceTemp.detail.*"));
//			QuotePriceDetailInfo detailInfo = QuotePriceDetailFactory.getLocalInstance(ctx).getQuotePriceDetailInfo(new ObjectUuidPK(strId), sic);
//			QuotePriceInfo quotePrice = detailInfo.getParent();// 牌价管理
//			PricePolicyInfo policyInfo = quotePrice.getPriceTemp();// 牌价模版
//			String policyInfoId = null;
//			if (policyInfo == null) {// 牌价模版为空查数据库第一条记录
//				IRowSet rowSet = SaleUtil.doSql(ctx, "select fid from T_ZB_SDBD_PricePolicy");
//				if (rowSet.next()) {
//					policyInfoId = rowSet.getString("fid");
//				} else {
//					return priceMap;
//				}
//			} else {
//				policyInfoId = policyInfo.getId().toString();
//			}
//			policyInfo = PricePolicyFactory.getLocalInstance(ctx).getPricePolicyInfo(new ObjectUuidPK(policyInfoId));
//			policyDetails = policyInfo.getDetail();
//
//			for (int i = 0; i < policyDetails.size(); i++) {
//				PricePolicyDetailInfo ppdi = policyDetails.get(i);
//				String code = ppdi.getPriceItemCode();
//				if (detailInfo.getBigDecimal(code) == null) {
//					continue;
//				}
//
//				if (ppdi.getProductStandard() == null) {
//					priceMap.put(ppdi.getSaleProduct().getId().toString(), detailInfo.getBigDecimal(code));
//				} else {
//					priceMap.put(ppdi.getSaleProduct().getId().toString() + "," + ppdi.getProductStandard().getId().toString(), detailInfo.getBigDecimal(code));
//				}
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		/*
//		 * while (iterator.hasNext()) { QuotePriceDetailInfo priceDetail =
//		 * (QuotePriceDetailInfo) iterator.next(); if (priceDetail != null) {
//		 * for (int i = 1; i <= 30; i++) { String priceItemCode = preFix + i; if
//		 * (fieldCodeMap.containsKey(priceItemCode)) { BigDecimal price =
//		 * priceDetail.getBigDecimal(priceItemCode);
//		 * priceMap.put(fieldCodeMap.get(priceItemCode), price); } } } }
//		 */
//		return priceMap;
//	}
//
//	/**
//	 *
//	 * @param ctx
//	 * @param productId
//	 * @param saleType
//	 * @return//根据规格、销售类型获取牌价对应的字段 (ctx, saleProductID, null, productStandID);
//	 * @throws EASBizException
//	 * @throws BOSException
//	 */
//
//	private String getPriceItemFieldName(Context ctx, String productId, SaleClassification saleType, String productStandard) throws EASBizException, BOSException {
//		IPricePolicyDetail ppd = PricePolicyDetailFactory.getLocalInstance(ctx);
//		String oql = "where saleProduct.id = '" + productId + "' and parent.usedStatus = 1 ";
//		if (saleType != null) {
//			oql += "and parent.saleType = " + saleType.getValue();
//		}
//		// if (productStandard != null) {
//		// oql += "and productStandard = '" + productStandard + "' ";
//		// }
//		// if (date != null) {
//		// oql += " and parent.startDate <= {" + DateHelper.getDTStyle4(date) +
//		// " 00:00:00" + "} and parent.endDate >={" +
//		// DateHelper.getDTStyle4(date) + " 00:00:00}";
//		// }
//		PricePolicyDetailCollection policyDetails = ppd.getPricePolicyDetailCollection(oql);
//		PricePolicyDetailInfo matchedInfo = null;
//		if (policyDetails == null) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品牌价模板失败，请检查牌价模板中是否包含该产品" });
//		}
//		boolean isProductStandardMatched = false;
//		boolean justSaleProductMatched = false;
//		for (int i = 0; i < policyDetails.size(); i++) {
//			PricePolicyDetailInfo info = policyDetails.get(i);
//			if (info.getProductStandard() == null) {
//				matchedInfo = info;
//				if (justSaleProductMatched) {
//					throw new ZBException(ZBException.TOOMANYRESULT, new String[] { "模板设置错误，重复的价格项！" });
//				}
//				justSaleProductMatched = true;
//			} else {
//				String pst = policyDetails.get(i).getProductStandard().getId().toString();
//				if (pst.equals(productStandard)) {
//					if (isProductStandardMatched) {
//						throw new ZBException(ZBException.TOOMANYRESULT, new String[] { "模板设置错误，重复的价格项！" });
//					}
//					isProductStandardMatched = true;
//					matchedInfo = policyDetails.get(i);
//				}
//			}
//		}
//		String code = null;
//		if (matchedInfo != null) {
//			code = matchedInfo.getPriceItemCode();
//		}
//		return code;
//	}
//
//
//	/**
//	 * 获取最近一次牌价（肥猪）
//	 * TANRONGTAO
//	 */
//	@Override
//	protected BigDecimal _getPriceLastFZ(Context ctx, String priceAreaID,
//			String saleProductID, String productStandID)
//			throws BOSException, EASBizException {
//
//		if (priceAreaID == null || priceAreaID.equals("")) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "价格区域为空！" });
//		}
//		//根据规格、销售类型获取牌价对应的字段
//		String itemCode = getPriceItemFieldName(ctx, saleProductID, null, productStandID);
//		if (itemCode == null || itemCode.equals("")) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品牌价模板失败，请检查牌价模板中是否包含该产品" });
//		}
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("/*dialect*/ \n");
//		sql.append("SELECT tab2.F"+itemCode+" "+itemCode+" \n");
//		sql.append("  FROM T_ZB_SD_QuotePrice tab1 \n");
//		sql.append("  LEFT JOIN T_ZB_SD_QuotePriceDetail tab2 ON tab1.fid=tab2.fparentid \n");
//		sql.append(" WHERE tab1.FSaleType=0 \n");
//		sql.append("   AND tab1.FBillStatus=10 \n");
//		sql.append("   AND tab2.FPriceAreaID='"+priceAreaID+"' \n");
//		sql.append("   AND tab2.F"+itemCode+" IS NOT NULL \n");
//		sql.append(" ORDER BY FBizDate DESC \n");
//		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
//		if(rs.size() == 0) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品单价失败，请检查该价格区域的牌价" });
//		}
//		try {
//			while(rs.next()){
//				return rs.getBigDecimal(itemCode);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * 获取最近一次牌价（种猪、仔猪）
//	 * TANRONGTAO
//	 */
//	@Override
//	protected IObjectValue _getPriceLastZZ(Context ctx, String priceAreaID, int pigSaletype,
//			String saleProductID, String productStandID) throws BOSException, EASBizException {
//
//		if (priceAreaID == null || priceAreaID.equals("")) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "本组织未在牌价区域中，请联系销售内勤进行维护后制单！" });
//		}
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("/*dialect*/ \n");
//		sql.append("SELECT tab2.FID \n");
//		sql.append("  FROM T_ZB_SD_QuotePrice tab1 \n");
//		sql.append("  LEFT JOIN T_ZB_SD_QuotePriceDetail tab2 ON tab1.fid=tab2.fparentid \n");
//		sql.append(" WHERE tab1.FSaleType="+pigSaletype+" \n");
//		sql.append("   AND tab1.FBillStatus=10 \n");
//		sql.append("   AND tab2.FPriceAreaID='"+priceAreaID+"' \n");
//		sql.append("   AND tab2.FSaleProductID='"+saleProductID+"' \n");
//		if(UIRuleUtil.isNotNull(productStandID)){
//			sql.append("   AND tab2.FProductStandardID='"+productStandID+"' \n");
//		}
//		sql.append(" ORDER BY FBizDate DESC \n");
//		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
//		if(rs.size() == 0) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取产品单价失败，请检查该价格区域的牌价" });
//		}
//		try {
//			while(rs.next()){
//				QuotePriceDetailInfo info = QuotePriceDetailFactory.getLocalInstance(ctx).getQuotePriceDetailInfo(new ObjectUuidPK(rs.getString("FID")));
//				return info;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * tanrongtao
//	 * 根据牌价区域、重量区间、参考品种获取肥猪价格系数的设置品种
//	 */
//	@Override
//	protected String _getAdjProductID(Context ctx, String priceAreaID,
//			String saleProductID, String productStandID) throws BOSException,
//			EASBizException {
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("/*dialect*/ \n");
//		sql.append("SELECT TAB2.FADJPRODUCTID \n");
//		sql.append("  FROM T_ZB_SDBD_PRICEFACTOR TAB1 \n");
//		sql.append("  LEFT JOIN T_ZSB_PRICEFACTORFACTORDETAIL TAB2 ON TAB1.FID = TAB2.FPARENTID \n");
//		sql.append(" WHERE TAB1.FUSEDSTATUS = "+UsedStatusEnum.ENABLED_VALUE+" \n");
//		sql.append("   AND TAB1.FQUOTEPRICEAREAID = '"+priceAreaID+"' \n");
//		sql.append("   AND FBASEPRODUCTID = '"+saleProductID+"' \n");
//		sql.append("   AND TAB2.FWEIGHTRANGEID = '"+productStandID+"' \n");
//		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
//		if(rs.size() == 0 || rs.size()>1) {
//			throw new ZBException(ZBException.CUSTOM, new String[] { "获取设置品种失败，请检查该牌价区域的肥猪价格系数是否包含或重复." });
//		}
//		try {
//			while(rs.next()){
//				return rs.getString("FADJPRODUCTID");   //设置品种
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//}