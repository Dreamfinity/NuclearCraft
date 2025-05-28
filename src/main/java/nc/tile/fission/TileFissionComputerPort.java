package nc.tile.fission;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.*;
import li.cil.oc.api.machine.*;
import li.cil.oc.api.network.SimpleComponent;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.fission.*;
import nc.util.OCHelper;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class TileFissionComputerPort extends TileFissionPart implements SimpleComponent {
	
	public TileFissionComputerPort() {
		super(CuboidalPartPositionType.WALL);
	}
	
	@Override
	public void onMachineAssembled(FissionReactor multiblock) {
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}
	
	// OpenComputers
	
	@Override
	@Optional.Method(modid = "opencomputers")
	public String getComponentName() {
		return "nc_salt_fission_reactor";
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] isComplete(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled()};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] isReactorOn(Context context, Arguments args) {
		FissionReactor reactor = getMultiblock();
		return new Object[] {reactor != null && reactor.isReactorOn};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getLengthX(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().getInteriorLengthX() : 0};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getLengthY(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().getInteriorLengthY() : 0};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getLengthZ(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().getInteriorLengthZ() : 0};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getHeatStored(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getLogic().heatBuffer.getHeatStored() : 0L};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getHeatCapacity(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getLogic().heatBuffer.getHeatCapacity() : 0L};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getTemperature(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getLogic().getTemperature() : 0D};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getCoolingRate(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().cooling : 0D};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getRawHeatingRate(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().rawHeating : 0D};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getMeanEfficiency(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().meanEfficiency : 0D};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getMeanHeatMultiplier(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().meanHeatMult : 0D};
	}
	
	protected <T extends IFissionPart> Object[] getPartCount(Class<T> type) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().getPartCount(type) : 0};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfIrradiators(Context context, Arguments args) {
		return getPartCount(TileFissionIrradiator.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfCells(Context context, Arguments args) {
		return getPartCount(TileSolidFissionCell.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfSinks(Context context, Arguments args) {
		return getPartCount(TileSolidFissionSink.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfVessels(Context context, Arguments args) {
		return getPartCount(TileSaltFissionVessel.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfHeaters(Context context, Arguments args) {
		return getPartCount(TileSaltFissionHeater.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfShields(Context context, Arguments args) {
		return getPartCount(TileFissionShield.class);
	}
	
	protected <T extends IFissionComponent> Object[] getPartStats(Class<T> type) {
		return new Object[] {isMultiblockAssembled() ? new Object[] {} : getMultiblock().getParts(type).stream().map(IFissionComponent::getOCInfo).toArray()};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getIrradiatorStats(Context context, Arguments args) {
		return getPartStats(TileFissionIrradiator.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getCellStats(Context context, Arguments args) {
		return getPartStats(TileSolidFissionCell.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getSinkStats(Context context, Arguments args) {
		return getPartStats(TileSolidFissionSink.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getVesselStats(Context context, Arguments args) {
		return getPartStats(TileSaltFissionVessel.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getHeaterStats(Context context, Arguments args) {
		return getPartStats(TileSaltFissionHeater.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getShieldStats(Context context, Arguments args) {
		return getPartStats(TileFissionShield.class);
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getNumberOfClusters(Context context, Arguments args) {
		return new Object[] {isMultiblockAssembled() ? getMultiblock().clusterCount : 0};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "opencomputers")
	public Object[] getClusterInfo(Context context, Arguments args) {
		int clusterID;
		FissionCluster cluster;
		if (!isMultiblockAssembled() || (clusterID = args.checkInteger(0)) >= getMultiblock().clusterCount || (cluster = getMultiblock().getClusterMap().get(clusterID)) == null) {
			return new Object[] {new Object[] {}};
		}
		
		Object2ObjectMap<String, Object> infoMap = new Object2ObjectLinkedOpenHashMap<>();
		infoMap.put("heat_capacity", cluster.heatBuffer.getHeatCapacity());
		infoMap.put("heat_stored", cluster.heatBuffer.getHeatStored());
		infoMap.put("cooling", cluster.cooling);
		infoMap.put("raw_heating", cluster.rawHeating);
		
		Object2ObjectMap<String, Object2ObjectMap<Object, Object>> componentsMap = new Object2ObjectLinkedOpenHashMap<>();
		for (Entry<IFissionComponent> entry : cluster.getComponentMap().long2ObjectEntrySet()) {
			IFissionComponent component = entry.getValue();
			String key = component.getOCKey();
			Object2ObjectMap<Object, Object> entryMap = componentsMap.get(key);
			if (entryMap == null) {
				componentsMap.put(key, entryMap = new Object2ObjectLinkedOpenHashMap<>());
			}
			entryMap.put(OCHelper.posInfo(entry.getLongKey()), component.getOCInfo());
		}
		infoMap.put("components", componentsMap);
		
		return new Object[] {infoMap};
	}
	
	@Callback
	@Optional.Method(modid = "opencomputers")
	public Object[] clearAllMaterial(Context context, Arguments args) {
		if (isMultiblockAssembled()) {
			getMultiblock().clearAllMaterial();
		}
		return new Object[] {};
	}
}
