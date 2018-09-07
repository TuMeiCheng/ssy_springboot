package com.wande.ssy.config;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.wande.ssy.entity.*;

/**
 * @author stanley
 */
public class JfinalORM {

	public static void mapping(ActiveRecordPlugin arp) {
	
		arp.addMapping("eqp_user","uin", User.class);

	    arp.addMapping("eqp_log", "logId", Log.class);

		arp.addMapping("eqp_admin", "uin", Admin.class);

		arp.addMapping("eqp_area", "areaId", Area.class);

		arp.addMapping("eqp_areaflow", "areaflowId", AreaFlow.class);

		arp.addMapping("eqp_brokenreason", "reasonId", Brokenreason.class);

		arp.addMapping("eqp_eqp", "eqpId", Eqp.class);

		arp.addMapping("eqp_eqpsort", "eqpsortId", EqpSort.class);

		arp.addMapping("eqp_export", "exportId", Export.class);

		arp.addMapping("eqp_item", "itemId", Item.class);

		arp.addMapping("eqp_itemflow", "itemflowId", ItemFlow.class);

		arp.addMapping("eqp_itemrepair", "repairId", ItemRepair.class);

		arp.addMapping("eqp_msg", "id", Msg.class);

		arp.addMapping("eqp_org", "orgId", Org.class);

		arp.addMapping("eqp_qrcode", "qrcodeId", Qrcode.class);

		arp.addMapping("eqp_region", "regionId", Region.class);

		arp.addMapping("eqp_regionagency", "id", RegionAgency.class);

		arp.addMapping("eqp_supplier", "supplierId", Supplier.class);

	}
}
