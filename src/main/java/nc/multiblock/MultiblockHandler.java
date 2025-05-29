package nc.multiblock;

import nc.multiblock.battery.BatteryMultiblock;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.hx.HeatExchanger;
import nc.multiblock.machine.Machine;
import nc.multiblock.quantum.QuantumComputer;
import nc.multiblock.rtg.RTGMultiblock;
import nc.multiblock.turbine.Turbine;
import nc.tile.battery.TileBattery;
import nc.tile.fission.*;
import nc.tile.fission.manager.*;
import nc.tile.fission.port.*;
import nc.tile.hx.*;
import nc.tile.machine.*;
import nc.tile.quantum.*;
import nc.tile.rtg.TileRTG;
import nc.tile.turbine.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.*;

public class MultiblockHandler {
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onChunkLoad(final ChunkEvent.Load loadEvent) {
		Chunk chunk = loadEvent.getChunk();
		MultiblockRegistry.INSTANCE.onChunkLoaded(loadEvent.getWorld(), chunk.x, chunk.z);
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onWorldUnload(final WorldEvent.Unload unloadWorldEvent) {
		MultiblockRegistry.INSTANCE.onWorldUnloaded(unloadWorldEvent.getWorld());
	}
	
	@SubscribeEvent
	public void onWorldTick(final TickEvent.WorldTickEvent event) {
		if (TickEvent.Phase.START == event.phase) {
			MultiblockRegistry.INSTANCE.tickStart(event.world);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (TickEvent.Phase.START == event.phase) {
			MultiblockRegistry.INSTANCE.tickStart(Minecraft.getMinecraft().world);
		}
	}
	
	public static void init() {
		Machine.PART_CLASSES.add(IMachineController.class);
		Machine.PART_CLASSES.add(TileMachineProcessPort.class);
		Machine.PART_CLASSES.add(TileMachineRedstonePort.class);
		Machine.PART_CLASSES.add(TileElectrolyzerCathodeTerminal.class);
		Machine.PART_CLASSES.add(TileElectrolyzerAnodeTerminal.class);
		Machine.PART_CLASSES.add(TileDistillerRefluxUnit.class);
		Machine.PART_CLASSES.add(TileDistillerReboilingUnit.class);
		Machine.PART_CLASSES.add(TileDistillerLiquidDistributor.class);
		Machine.PART_CLASSES.add(TileInfiltratorPressureChamber.class);
		Machine.PART_CLASSES.add(TileInfiltratorHeatingUnit.class);
		
		RTGMultiblock.PART_CLASSES.add(TileRTG.class);
		
		BatteryMultiblock.PART_CLASSES.add(TileBattery.class);
		
		FissionReactor.PART_CLASSES.add(IFissionController.class);
		FissionReactor.PART_CLASSES.add(IFissionComponent.class);
		FissionReactor.PART_CLASSES.add(IFissionSpecialPart.class);
		FissionReactor.PART_CLASSES.add(TileFissionConductor.class);
		FissionReactor.PART_CLASSES.add(TileFissionMonitor.class);
		FissionReactor.PART_CLASSES.add(TileFissionVent.class);
		FissionReactor.PART_CLASSES.add(TileFissionIrradiatorPort.class);
		FissionReactor.PART_CLASSES.add(TileFissionCoolerPort.class);
		FissionReactor.PART_CLASSES.add(TileFissionCellPort.class);
		FissionReactor.PART_CLASSES.add(TileFissionVesselPort.class);
		FissionReactor.PART_CLASSES.add(TileFissionHeaterPort.class);
		FissionReactor.PART_CLASSES.add(TileFissionSourceManager.class);
		FissionReactor.PART_CLASSES.add(TileFissionShieldManager.class);
		FissionReactor.PART_CLASSES.add(TileFissionIrradiator.class);
		FissionReactor.PART_CLASSES.add(TileFissionCooler.class);
		FissionReactor.PART_CLASSES.add(TileFissionSource.class);
		FissionReactor.PART_CLASSES.add(TileFissionShield.class);
		FissionReactor.PART_CLASSES.add(TileSolidFissionCell.class);
		FissionReactor.PART_CLASSES.add(TileSolidFissionSink.class);
		FissionReactor.PART_CLASSES.add(TileSaltFissionVessel.class);
		FissionReactor.PART_CLASSES.add(TileSaltFissionHeater.class);
		
		HeatExchanger.PART_CLASSES.add(IHeatExchangerController.class);
		HeatExchanger.PART_CLASSES.add(TileHeatExchangerInlet.class);
		HeatExchanger.PART_CLASSES.add(TileHeatExchangerOutlet.class);
		HeatExchanger.PART_CLASSES.add(TileHeatExchangerTube.class);
		HeatExchanger.PART_CLASSES.add(TileHeatExchangerBaffle.class);
		HeatExchanger.PART_CLASSES.add(TileHeatExchangerRedstonePort.class);
		
		Turbine.PART_CLASSES.add(ITurbineController.class);
		Turbine.PART_CLASSES.add(TileTurbineDynamoPart.class);
		Turbine.PART_CLASSES.add(TileTurbineRotorShaft.class);
		Turbine.PART_CLASSES.add(TileTurbineRotorBlade.class);
		Turbine.PART_CLASSES.add(TileTurbineRotorStator.class);
		Turbine.PART_CLASSES.add(TileTurbineRotorBearing.class);
		Turbine.PART_CLASSES.add(TileTurbineInlet.class);
		Turbine.PART_CLASSES.add(TileTurbineOutlet.class);
		Turbine.PART_CLASSES.add(TileTurbineRedstonePort.class);
		
		QuantumComputer.PART_CLASSES.add(TileQuantumComputerController.class);
		QuantumComputer.PART_CLASSES.add(TileQuantumComputerQubit.class);
		QuantumComputer.PART_CLASSES.add(TileQuantumComputerCodeGenerator.class);
	}
}
