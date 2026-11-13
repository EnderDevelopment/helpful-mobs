# Minecraft Plugin: Enhanced Helper

Enhanced Helper is a Minecraft plugin that adds helpful mob behaviors to tamed wolves, tamed cats, and villagers. It provides automatic wolf healing, cat danger warnings, and villager food gifts for players who need assistance.

## Install

1. Build the plugin with Java 21 and Maven:
   ```bash
   mvn package
   ```
2. Copy the generated `target/plugin.jar` file into your Paper server's `plugins` directory.
3. Start or restart the server.
4. Confirm that `HelpfulMobs` appears as enabled in the server log.

Requirements:

- Paper Minecraft server 1.21.11 or a compatible 1.21 server
- Java 21

## Use

- Tame a wolf and keep it within 12 blocks horizontally and 6 blocks vertically. When the wolf's owner takes non-fatal damage, the wolf restores 2 health points to the player.
- Tame a cat and keep it near areas where hostile mobs may spawn. When a listed hostile mob spawns within 16 blocks horizontally and 8 blocks vertically, the cat warns its owner with an action-bar message.
- Interact with a villager while your food level is 14 or lower. The villager gives you two bread items, with a 60-second cooldown per player.
- The plugin works automatically after installation and requires no commands or configuration files.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/79ad153e-8ae2-4082-a127-5336f7ba8a96?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=79ad153e-8ae2-4082-a127-5336f7ba8a96
