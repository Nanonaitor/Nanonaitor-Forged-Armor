package com.nanonaitor.forgedarmor;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class ForgedArmorEvents {
    private static final UUID SPEED_ID = UUID.fromString("57c90da8-f3ee-4c07-ae93-a4a53e645c81");
    private static final UUID ARMOR_ID = UUID.fromString("44bd70af-6e7d-40f2-8854-8a5c4d8440dc");
    private static final UUID KNOCKBACK_ID = UUID.fromString("a7f3cc83-ea83-4d73-b32f-a8212ce99731");
    private static final UUID SWIM_ID = UUID.fromString("7c2aef4a-54fc-4385-b4dc-ae3a0c9c0fa0");

    @SubscribeEvent
    public void mobDrops(LivingDropsEvent event) {
        if (!ForgedArmorConfig.ENABLE_MOB_DROPS.get()) return;
        Entity killerEntity = event.getSource().getEntity();
        Player killer = killerEntity instanceof Player player ? player : null;
        int looting = killer == null ? 0 : EnchantmentHelper.getMobLooting(killer);
        int luck = killer == null || !killer.hasEffect(MobEffects.LUCK) ? 0
                : killer.getEffect(MobEffects.LUCK).getAmplifier() + 1;
        double chance = ForgedArmorConfig.BASE_DROP_CHANCE.get()
                + looting * ForgedArmorConfig.LOOTING_BONUS_PER_LEVEL.get()
                + luck * ForgedArmorConfig.LUCK_BONUS_PER_LEVEL.get();
        ResourceLocation entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        String entityId = entityKey.toString();
        for (AttachmentType type : AttachmentType.values()) {
            if (!type.dropsFrom(entityId) || event.getEntity().getRandom().nextDouble() >= Math.min(1.0, chance)) continue;
            ItemStack drop = new ItemStack(ForgedArmor.ATTACHMENTS.get(type).get());
            event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(),
                    event.getEntity().getY(), event.getEntity().getZ(), drop));
        }
    }

    @SubscribeEvent
    public void hurt(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        Set<AttachmentType> victimAttachments = AttachmentData.equipped(victim);
        Entity attackerEntity = event.getSource().getEntity();

        if (attackerEntity instanceof Player attacker) {
            Set<AttachmentType> attackerAttachments = AttachmentData.equipped(attacker);
            boolean projectile = event.getSource().is(DamageTypeTags.IS_PROJECTILE);
            if (!projectile && attackerAttachments.contains(AttachmentType.RAVAGER_HORN))
                event.setAmount(event.getAmount() * (1.0F + ForgedArmorConfig.MELEE_BONUS.get().floatValue()));
            if (projectile && attackerAttachments.contains(AttachmentType.HOGLIN_HORN))
                event.setAmount(event.getAmount() * (1.0F + ForgedArmorConfig.RANGED_BONUS.get().floatValue()));
            if (event.getSource().is(DamageTypes.MAGIC) && attackerAttachments.contains(AttachmentType.GOAT_HORN))
                event.setAmount(event.getAmount() * (1.0F + ForgedArmorConfig.MAGIC_BONUS.get().floatValue()));
        }

        if (event.getSource().is(DamageTypeTags.IS_PROJECTILE) && victimAttachments.contains(AttachmentType.SHULKER_PLATES))
            event.setAmount(event.getAmount() * (1.0F - ForgedArmorConfig.PROJECTILE_REDUCTION.get().floatValue()));
        if (attackerEntity instanceof LivingEntity living && living.getMobType() == MobType.ARTHROPOD
                && victimAttachments.contains(AttachmentType.SILVERFISH_CARAPACE))
            event.setAmount(event.getAmount() * (1.0F - ForgedArmorConfig.ARTHROPOD_REDUCTION.get().floatValue()));

        if (victim instanceof Player player && player.isUsingItem() && player.getUseItem().getItem() instanceof ShieldItem
                && victimAttachments.contains(AttachmentType.TURTLE_SHELL))
            event.setAmount(event.getAmount() * 0.75F);

        if (attackerEntity instanceof LivingEntity attacker && attacker != victim) {
            if (victimAttachments.contains(AttachmentType.BEE_STINGER))
                attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            if (victimAttachments.contains(AttachmentType.GUARDIAN_SPIKES))
                attacker.hurt(victim.damageSources().thorns(victim), 2.0F);
            if (victimAttachments.contains(AttachmentType.HOGLIN_HOOVES)) {
                double dx = attacker.getX() - victim.getX();
                double dz = attacker.getZ() - victim.getZ();
                attacker.knockback(ForgedArmorConfig.KNOCKBACK_DEALT.get(), -dx, -dz);
            }
        }
    }

    @SubscribeEvent
    public void attack(LivingAttackEvent event) {
        LivingEntity victim = event.getEntity();
        if (!AttachmentData.equipped(victim).contains(AttachmentType.CREAKING_HEART)) return;
        long now = victim.level().getGameTime();
        long ready = victim.getPersistentData().getLong("ForgedArmorCreakingReady");
        if (now < ready) return;
        victim.getPersistentData().putLong("ForgedArmorCreakingReady",
                now + ForgedArmorConfig.PERIODIC_COOLDOWN_SECONDS.get() * 20L);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void fall(LivingFallEvent event) {
        Set<AttachmentType> attachments = AttachmentData.equipped(event.getEntity());
        if (attachments.contains(AttachmentType.PARROT_TAIL)) {
            event.setCanceled(true);
        } else if (attachments.contains(AttachmentType.SLIME) && event.getDistance() > 2) {
            event.setDamageMultiplier(0);
            event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().x,
                    Math.min(1.25, 0.25 + event.getDistance() * 0.08), event.getEntity().getDeltaMovement().z);
            event.getEntity().hasImpulse = true;
        }
    }

    @SubscribeEvent
    public void target(LivingChangeTargetEvent event) {
        if (!(event.getNewTarget() instanceof Player player)) return;
        Set<AttachmentType> attachments = AttachmentData.equipped(player);
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType()).getPath();
        boolean neutral = (attachments.contains(AttachmentType.PILLAGER_NOSE) && id.equals("pillager"))
                || (attachments.contains(AttachmentType.PIGLIN_HOOVES) && (id.equals("piglin") || id.equals("piglin_brute")))
                || (attachments.contains(AttachmentType.ENDER_DRAGON_MASK) && id.equals("enderman"))
                || (attachments.contains(AttachmentType.ZOMBIE_MASK) && (id.equals("zombie") || id.equals("husk") || id.equals("drowned")))
                || (attachments.contains(AttachmentType.SKELETON_MASK) && (id.equals("skeleton") || id.equals("stray")))
                || (attachments.contains(AttachmentType.CREEPER_MASK) && id.equals("creeper"))
                || (attachments.contains(AttachmentType.SPIDER_MASK) && (id.equals("spider") || id.equals("cave_spider")));
        if (neutral) event.setCanceled(true);
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        Set<AttachmentType> attachments = AttachmentData.equipped(player);
        updateModifier(player, Attributes.MOVEMENT_SPEED, SPEED_ID, "Forged armor speed",
                attachments.contains(AttachmentType.BUNNY_EARS) || attachments.contains(AttachmentType.RABBIT_FEET) ? 0.5 : 0,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        updateModifier(player, Attributes.ARMOR, ARMOR_ID, "Forged armor plate",
                attachments.contains(AttachmentType.IRON_GOLEM_PLATE) ? 6.0 : 0, AttributeModifier.Operation.ADDITION);
        updateModifier(player, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_ID, "Forged armor hide",
                attachments.contains(AttachmentType.RAVAGER_HIDE) || attachments.contains(AttachmentType.ELDER_GUARDIAN_SPIKES)
                        ? ForgedArmorConfig.KNOCKBACK_RESISTANCE.get() : 0,
                AttributeModifier.Operation.ADDITION);
        if (!player.level().isClientSide && player.tickCount % 20 == 0) {
            if (attachments.contains(AttachmentType.WARDEN_EARS)) {
                int range = ForgedArmorConfig.WARDEN_EARS_RANGE.get();
                for (LivingEntity nearby : player.level().getEntitiesOfClass(LivingEntity.class,
                        player.getBoundingBox().inflate(range), e -> e != player))
                    nearby.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, true, false));
            }
            removeTemperatureEffect(player, attachments);
        }

        if (!player.level().isClientSide && player.tickCount % (ForgedArmorConfig.PERIODIC_COOLDOWN_SECONDS.get() * 20) == 0) {
            if (attachments.contains(AttachmentType.WITCH_HEART)) removeOneDebuff(player);
            if (attachments.contains(AttachmentType.IRON_GOLEM_HEART)) repairRandomItem(player);
            if (attachments.contains(AttachmentType.WARDEN_HEART)) distractOneEnemy(player);
        }

        if (player.onGround()) player.getPersistentData().putBoolean("ForgedArmorAirJumpUsed", false);
        if (attachments.contains(AttachmentType.STRIDER_FEET) && player.isInLava()) {
            player.clearFire();
            if (player.getDeltaMovement().y < 0.08) player.setDeltaMovement(player.getDeltaMovement().x, 0.08, player.getDeltaMovement().z);
            player.fallDistance = 0;
        }
        if (attachments.contains(AttachmentType.SPIDER_LEGS) && player.horizontalCollision)
            player.setDeltaMovement(player.getDeltaMovement().x, 0.20, player.getDeltaMovement().z);
        if (attachments.contains(AttachmentType.FROG_LEGS) && player.isInWater()) {
            Vec3 motion = player.getDeltaMovement();
            double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            if (horizontal > 0.01 && horizontal < 0.60)
                player.setDeltaMovement(motion.x * 1.08, motion.y, motion.z * 1.08);
        }
        if (attachments.contains(AttachmentType.ELDER_GUARDIAN_SPIKES)
                && player.level().getBlockState(player.blockPosition().below()).is(BlockTags.ICE)
                && Math.abs(player.xxa) < 0.01 && Math.abs(player.zza) < 0.01) {
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.x * 0.25, motion.y, motion.z * 0.25);
        }
    }

    @SubscribeEvent
    public void jump(LivingEvent.LivingJumpEvent event) {
        Set<AttachmentType> attachments = AttachmentData.equipped(event.getEntity());
        if (attachments.contains(AttachmentType.RABBIT_FEET) || attachments.contains(AttachmentType.FROG_LEGS)) {
            Vec3 motion = event.getEntity().getDeltaMovement();
            event.getEntity().setDeltaMovement(motion.x, motion.y + 0.10, motion.z);
        }
    }

    private static void updateModifier(Player player, net.minecraft.world.entity.ai.attributes.Attribute attribute,
                                       UUID id, String name, double amount, AttributeModifier.Operation operation) {
        var instance = player.getAttribute(attribute);
        if (instance == null) return;
        AttributeModifier old = instance.getModifier(id);
        if (old != null) instance.removeModifier(old);
        if (amount != 0) instance.addTransientModifier(new AttributeModifier(id, name, amount, operation));
    }

    private static void removeOneDebuff(Player player) {
        List<MobEffectInstance> harmful = new ArrayList<>();
        for (MobEffectInstance effect : player.getActiveEffects()) if (!effect.getEffect().isBeneficial()) harmful.add(effect);
        if (!harmful.isEmpty()) player.removeEffect(harmful.get(player.getRandom().nextInt(harmful.size())).getEffect());
    }

    private static void repairRandomItem(Player player) {
        List<ItemStack> damaged = new ArrayList<>();
        for (ItemStack stack : player.getInventory().items) if (stack.isDamaged()) damaged.add(stack);
        for (ItemStack stack : player.getInventory().armor) if (stack.isDamaged()) damaged.add(stack);
        if (!damaged.isEmpty()) {
            ItemStack stack = damaged.get(player.getRandom().nextInt(damaged.size()));
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
        }
    }

    private static void distractOneEnemy(Player player) {
        player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(32),
                        mob -> mob.getTarget() == player)
                .stream().min(Comparator.comparingDouble(player::distanceToSqr)).ifPresent(mob -> {
                    mob.setTarget(null);
                    mob.addEffect(new MobEffectInstance(MobEffects.GLOWING,
                            ForgedArmorConfig.PERIODIC_COOLDOWN_SECONDS.get() * 20, 0));
                });
    }

    private static void removeTemperatureEffect(Player player, Set<AttachmentType> attachments) {
        for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(instance.getEffect());
            String path = key.getPath().toLowerCase();
            if (attachments.contains(AttachmentType.SNOW_GOLEM_ICE_CORE) && path.contains("hyperthermia"))
                player.removeEffect(instance.getEffect());
            if (attachments.contains(AttachmentType.IRON_GOLEM_IRON_CORE) && path.contains("hypothermia"))
                player.removeEffect(instance.getEffect());
        }
    }

    @SubscribeEvent
    public void tooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Equipable equipable = Equipable.get(stack);
        if (equipable == null || equipable.getEquipmentSlot().getType() != EquipmentSlot.Type.ARMOR) return;
        for (AttachmentSlot slot : AttachmentSlot.values()) {
            AttachmentType type = AttachmentType.byId(AttachmentData.getId(stack, slot));
            if (type != null) event.getToolTip().add(net.minecraft.network.chat.Component.literal(
                    slot.displayName().getString() + ": " + type.displayName()).withStyle(net.minecraft.ChatFormatting.GREEN));
        }
    }
}
