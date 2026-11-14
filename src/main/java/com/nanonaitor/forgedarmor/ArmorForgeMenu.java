package com.nanonaitor.forgedarmor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public final class ArmorForgeMenu extends AbstractContainerMenu {
    private final Container input = new SimpleContainer(2);
    private final ResultContainer result = new ResultContainer();
    private final ContainerLevelAccess access;

    public ArmorForgeMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, data.readBlockPos());
    }

    public ArmorForgeMenu(int id, Inventory inventory, BlockPos pos) {
        super(ForgedArmor.ARMOR_FORGE_MENU.get(), id);
        this.access = ContainerLevelAccess.create(inventory.player.level(), pos);

        addSlot(new Slot(input, 0, 27, 47) {
            @Override public boolean mayPlace(ItemStack stack) {
                Equipable equipable = Equipable.get(stack);
                return equipable != null && equipable.getEquipmentSlot().getType() == net.minecraft.world.entity.EquipmentSlot.Type.ARMOR;
            }
            @Override public void setChanged() { super.setChanged(); updateResult(); }
        });
        addSlot(new Slot(input, 1, 76, 47) {
            @Override public boolean mayPlace(ItemStack stack) {
                AttachmentType type = AttachmentData.attachmentType(stack);
                return type != null && type.enabled();
            }
            @Override public void setChanged() { super.setChanged(); updateResult(); }
        });
        addSlot(new Slot(result, 0, 134, 47) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
            @Override public void onTake(Player player, ItemStack stack) {
                ItemStack armor = input.getItem(0);
                ItemStack attachment = input.getItem(1);
                AttachmentType incoming = AttachmentData.attachmentType(attachment);
                if (incoming != null) {
                    AttachmentType previous = AttachmentType.byId(AttachmentData.getId(armor, incoming.slot()));
                    if (previous != null) giveBack(player, previous);
                } else {
                    // Removal: armor on its own comes out bare and every attachment goes back to the player.
                    for (AttachmentType removed : AttachmentData.attached(armor)) giveBack(player, removed);
                }
                input.removeItem(0, 1);
                input.removeItem(1, 1);
                if (!player.level().isClientSide) {
                    boolean removing = incoming == null;
                    access.execute((level, pos) -> level.playSound(null, pos,
                            removing ? SoundEvents.GRINDSTONE_USE : SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F));
                }
                updateResult();
                super.onTake(player, stack);
            }
        });

        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++)
            addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        for (int col = 0; col < 9; col++) addSlot(new Slot(inventory, col, 8 + col * 18, 142));
    }

    private static void giveBack(Player player, AttachmentType type) {
        ItemStack returned = AttachmentData.attachmentStack(type);
        if (!player.getInventory().add(returned)) player.drop(returned, false);
    }

    public ItemStack armor() { return getSlot(0).getItem(); }
    public ItemStack attachment() { return getSlot(1).getItem(); }

    /** True when the forge is set to strip every attachment off the armor (armor in, nothing to attach). */
    public boolean isRemoving() {
        return !armor().isEmpty() && attachment().isEmpty() && !AttachmentData.attached(armor()).isEmpty();
    }

    private void updateResult() {
        ItemStack armor = input.getItem(0);
        ItemStack attachment = input.getItem(1);
        AttachmentType type = AttachmentData.attachmentType(attachment);
        if (!armor.isEmpty() && attachment.isEmpty() && !AttachmentData.attached(armor).isEmpty()) {
            result.setItem(0, AttachmentData.strip(armor));
        } else if (armor.isEmpty() || type == null || !type.enabled() || !AttachmentData.armorAccepts(armor, type)) {
            result.setItem(0, ItemStack.EMPTY);
        } else {
            result.setItem(0, AttachmentData.attach(armor, type));
        }
        broadcastChanges();
    }

    @Override public boolean stillValid(Player player) {
        return stillValid(access, player, ForgedArmor.ARMOR_FORGE.get());
    }

    @Override public void removed(Player player) {
        super.removed(player);
        access.execute((level, pos) -> clearContainer(player, input));
    }

    @Override public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();
        if (index == 2) {
            if (!moveItemStackTo(original, 3, 39, true)) return ItemStack.EMPTY;
            slot.onQuickCraft(original, copy);
        } else if (index >= 3) {
            if (AttachmentData.attachmentType(original) != null) {
                if (!moveItemStackTo(original, 1, 2, false)) return ItemStack.EMPTY;
            } else if (Equipable.get(original) != null && Equipable.get(original).getEquipmentSlot().getType() == net.minecraft.world.entity.EquipmentSlot.Type.ARMOR) {
                if (!moveItemStackTo(original, 0, 1, false)) return ItemStack.EMPTY;
            } else return ItemStack.EMPTY;
        } else if (!moveItemStackTo(original, 3, 39, false)) return ItemStack.EMPTY;
        if (original.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        if (original.getCount() == copy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, original);
        return copy;
    }
}
