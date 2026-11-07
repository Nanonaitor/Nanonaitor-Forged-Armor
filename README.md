# Nanonaitor Forged Armor

A Minecraft Forge 1.20.1 mod by Nanonaitor. Use the green Armor Forge to permanently attach mob trophies and special components to armor while preserving the armor's enchantments, durability and other NBT.

## Armor Forge

Craft the Armor Forge from three Oxidized Copper blocks and four Iron blocks. Place armor in the left slot and an attachment in the middle slot, then take the forged armor from the output. Each armor piece has two named slots:

- Helmet: Horns and Face
- Chestplate: Chest and Back
- Leggings: Knees and Tail
- Boots: Soles and Top

Installing another attachment into an occupied slot replaces it and returns the previous attachment to the player.

## Drops

Every eligible mob attachment uses a configurable 5% base drop chance. Looting adds 4% per level and the Luck status effect adds 4% per level by default. All three values and mob drops as a whole are configurable in `nanonaitor_forged_armor-common.toml`.

The Ender Dragon Mask has a separate configurable 5% chance to appear in End City treasure chests.

## Controls

- `G`: open Ender Chest storage while wearing a chestplate forged with a Shulker Shell.
- Jump once more in midair with Allay or Vex Wings.
- Hold jump while falling with Phantom Wings to glide.
- Double-tap a movement key with Breeze Wind to dash in that direction.

## Compatibility notes

- Forge: 47.4.18 or newer
- Minecraft: 1.20.1
- Modded armor is supported when it exposes a normal armor equipment slot through Minecraft's `Equipable` interface.
- Creaking Heart and Breeze Wind drops activate when a compatible mod registers `minecraft:creaking`, `palebloom:creaking`, or `minecraft:breeze` on 1.20.1.
- Snow Golem Ice Core and Iron Golem Iron Core remove effects whose registry paths contain `hyperthermia` and `hypothermia` respectively.
- Magic damage uses Minecraft's magic damage type. Additional modded magic-damage systems may need dedicated compatibility.
- Donkey Tail stack-size expansion and Camel Tail thirst integration require dedicated container/thirst-mod hooks and are retained as planned compatibility work rather than unsafe global changes.

Visible 3D attachment models are planned for a later release; the installed attachment IDs already persist directly on the armor for that renderer.
