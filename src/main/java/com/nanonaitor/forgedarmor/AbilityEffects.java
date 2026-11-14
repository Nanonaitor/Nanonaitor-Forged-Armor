package com.nanonaitor.forgedarmor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

/** Server-side particle and sound feedback for attachment abilities; does nothing on the client. */
public final class AbilityEffects {
    private AbilityEffects() {}

    /** Particles spread around the entity's body, visible to everyone nearby. */
    public static void burst(Entity entity, ParticleOptions particle, int count, double spread) {
        if (!(entity.level() instanceof ServerLevel level)) return;
        level.sendParticles(particle, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(),
                count, spread, entity.getBbHeight() * 0.3, spread, 0.02);
    }

    /** Particles at the entity's feet, for jumps and dashes. */
    public static void puff(Entity entity, ParticleOptions particle, int count) {
        if (!(entity.level() instanceof ServerLevel level)) return;
        level.sendParticles(particle, entity.getX(), entity.getY() + 0.1, entity.getZ(), count, 0.3, 0.05, 0.3, 0.03);
    }

    public static void play(Entity entity, SoundEvent sound, float volume, float pitch) {
        if (entity.level().isClientSide) return;
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, volume, pitch);
    }
}
