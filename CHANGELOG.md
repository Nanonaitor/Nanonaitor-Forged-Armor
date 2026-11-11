# 0.1.4

- Removed Donkey Tail and cleanly ignored its old missing item mapping.
- Disabled Camel Tail pending Tough As Nails thirst and temperature compatibility.
- Replaced duplicate attachments with their proper vanilla Goat Horn, mob head, Turtle Shell, Shulker Shell, Rabbit's Foot, Slimeball and Elytra items.
- Added automatic remapping from the removed duplicate item IDs to their vanilla equivalents for existing worlds.
- Corrected Hoglin Hooves so melee attacks made by the wearer knock the target away.
- Corrected Strider Feet so players can stand and walk directly on lava using Minecraft's fluid-standing behavior.
- Restricted Warden Ears highlighting to hostile mobs and mobs actively targeting the wearer.

# 0.1.3

- Added the vanilla Elytra as a chestplate Back-slot attachment.
- Added normal Elytra takeoff, flight physics, sounds and flight animation while keeping the chestplate equipped.
- Rendered Minecraft's animated Elytra wings as part of the forged chestplate.
- Redirected Elytra flight wear to the chestplate at the vanilla rate of one durability per second.
- Made flight durability respect Unbreaking and stop at one remaining durability instead of breaking the chestplate.
- Added replacement support that returns an Elytra when another Back attachment replaces it.

# 0.1.2

- Added a dedicated Nanonaitor Forged Armor creative-mode tab.
- Used the green Armor Forge as the tab icon.
- Added the Armor Forge and every attachment to the tab in attachment-list order.

# 0.1.1

- Added equipped 3D visuals for every armor attachment.
- Anchored horns, masks, chest pieces, wings, tails, knee pieces and footwear to the matching animated player body part.
- Added distinct silhouettes for horns, ears, masks, hearts, cores, shells, wings, tails, plates, spikes, feet, hooves and spider legs.
- Reused source-mob textures so each attachment visually refers back to the creature that supplies it.
- Kept every attachment as an independent model child for easy per-item resizing and placement adjustments.

# 0.1.0

- Added the green Armor Forge workstation.
- Added persistent Horns, Face, Chest, Back, Knees, Tail, Soles and Top armor slots.
- Added the first 40 mob and treasure attachments.
- Added configurable mob drop, Looting and Luck chances.
- Added End City treasure support for the Ender Dragon Mask.
- Added combat, movement, defense, neutrality, repair, cleansing, storage, highlighting and mobility abilities.
- Added replacement support that returns an attachment already installed in the same slot.
- Added initial compatibility hooks for temperature effects, Pale Bloom Creakings and backported Breeze entities.
