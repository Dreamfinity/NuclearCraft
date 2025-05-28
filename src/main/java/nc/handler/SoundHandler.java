package nc.handler;

import it.unimi.dsi.fastutil.objects.*;
import nc.init.NCSounds;
import nc.util.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;

/**
 * Block sound handling - thanks to the Mekanism devs for this system!
 */
@SideOnly(Side.CLIENT)
public class SoundHandler {
	
	protected static final Object2ObjectMap<Vec3f, ISound> BLOCK_SOUND_MAP = new Object2ObjectOpenHashMap<>();
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayBlockSound(PlaySoundEvent event) {
		// Ignore any sound event which is null or is happening in a muffled check
		ISound resultSound = event.getResultSound();
		if (resultSound == null) {
			return;
		}
		
		// Ignore any unrelated sound event
		ISound originalSound = event.getSound();
		ResourceLocation soundName = originalSound.getSoundLocation();
		if (!NCSounds.TRACKABLE_SOUNDS.contains(soundName.toString())) {
			return;
		}
		
		// Make sure sound accessor is not null
		if (resultSound.getSound() == null) {
			resultSound.createAccessor(Minecraft.getMinecraft().getSoundHandler());
		}
		
		// At this point, we've got a known block sound
		resultSound = new BlockSound(originalSound, resultSound.getVolume(), resultSound.getPitch());
		event.setResultSound(resultSound);
		
		// Finally, update our soundMap so that we can actually have a shot at stopping this sound
		BLOCK_SOUND_MAP.put(new Vec3f(resultSound.getXPosF(), resultSound.getYPosF(), resultSound.getZPosF()), resultSound);
	}
	
	public static ISound startBlockSound(SoundEvent soundEvent, BlockPos pos, float volume, float pitch) {
		net.minecraft.client.audio.SoundHandler sounds = Minecraft.getMinecraft().getSoundHandler();
		
		// First, check to see if there's already a sound playing at the desired location
		Vec3f vec = new Vec3f(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
		ISound sound = BLOCK_SOUND_MAP.get(vec);
		if (sound == null || !sounds.isSoundPlaying(sound)) {
			if (sound != null) {
				sounds.stopSound(sound);
			}
			
			// No sound playing, start one up
			// We assume that the sound will play until explicitly stopped
			sound = new PositionedSoundRecord(soundEvent.getSoundName(), SoundCategory.BLOCKS, volume, pitch, true, 0, ISound.AttenuationType.LINEAR, vec.x, vec.y, vec.z) {
				
				@Override
				public float getVolume() {
					if (sound == null) {
						createAccessor(sounds);
					}
					return super.getVolume();
				}
				
				@Override
				public float getPitch() {
					if (sound == null) {
						createAccessor(sounds);
					}
					return super.getPitch();
				}
			};
			
			// Start the sound, firing a PlaySoundEvent
			sounds.playSound(sound);
			
			// N.B. By the time playSound returns, our expectation is that our wrapping-detector handler has fired
			// and dealt with any muting interceptions and, CRITICALLY, updated the soundMap with the final ISound.
			sound = BLOCK_SOUND_MAP.get(vec);
		}
		return sound;
	}
	
	public static void stopBlockSound(BlockPos pos) {
		Vec3f vec = new Vec3f(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
		ISound sound = BLOCK_SOUND_MAP.get(vec);
		if (sound != null) {
			Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
			BLOCK_SOUND_MAP.remove(vec);
		}
	}
	
	protected static class BlockSound implements ISound {
		
		private final ISound sound;
		private final float volume, pitch;
		
		BlockSound(ISound sound, float volume, float pitch) {
			this.sound = sound;
			this.volume = volume;
			this.pitch = pitch;
		}
		
		@Override
		public ResourceLocation getSoundLocation() {
			return sound.getSoundLocation();
		}
		
		@Override
		public @Nullable SoundEventAccessor createAccessor(net.minecraft.client.audio.SoundHandler handler) {
			return sound.createAccessor(handler);
		}
		
		@Override
		public net.minecraft.client.audio.Sound getSound() {
			return sound.getSound();
		}
		
		@Override
		public SoundCategory getCategory() {
			return sound.getCategory();
		}
		
		@Override
		public boolean canRepeat() {
			return sound.canRepeat();
		}
		
		@Override
		public int getRepeatDelay() {
			return sound.getRepeatDelay();
		}
		
		@Override
		public float getVolume() {
			return volume;
		}
		
		@Override
		public float getPitch() {
			return pitch;
		}
		
		@Override
		public float getXPosF() {
			return sound.getXPosF();
		}
		
		@Override
		public float getYPosF() {
			return sound.getYPosF();
		}
		
		@Override
		public float getZPosF() {
			return sound.getZPosF();
		}
		
		@Override
		public AttenuationType getAttenuationType() {
			return sound.getAttenuationType();
		}
	}
}
