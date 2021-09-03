Adds data options for teleporting players to and from different dimensions when at specific Y levels!

### Dimension Config:
Default config teleports the player to the nether when at bedrock, and to the end when at max build height.

The example config for teleporting the player to the nether when at Y level -10 to 1:
```json
{
    "from": "minecraft:overworld",
    "to": "minecraft:the_nether",
    "from_y_min": -10,
    "from_y_max": 1,
    "to_y": 121,
    "cloud_platform": true
}
```

| Key | Value | Description |
| --- | --- | --- |
| from | String | Dimension ID the player has to be in to trigger the teleport |
| to | String | Dimension ID the player will be teleported to |
| from_y_min | Integer | Minimum Y the player must be at to trigger the teleport |
| from_y_max | Integer | Maximum Y the player must be at to trigger the teleport |
| to_y | Integer | Y level the player will be teleported at |
| cloud_platform | Boolean | If a cloud platform should be generated beneath the teleport destination |

### Additional Info:
- Clouds are blocks that can be spawned below the teleport destination to keep the player from falling back down. They will disappear over time.
- Teleporting does not modify X and Z values, so nether distance per overworld block is not preserved.
