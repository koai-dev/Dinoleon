package com.github.quillraven.dinoleon.system

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.dinoleon.component.ImageComponent
import com.github.quillraven.dinoleon.event.SpawnRemovalEvent
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject

class DespawnSystem(
    private val stage: Stage = inject()
) : IteratingSystem(family { all(ImageComponent) }) {
    override fun onTickEntity(entity: Entity) {
        if (entity[ImageComponent].image.x <= -1f) {
            // important to remove entity first before firing the event
            // because the event could cause a "removeAll" call in the GameScreen
            // which also recreates the player entity and therefore
            // and therefore this entity could link to a recycled player instead
            world.remove(entity)
            stage.root.fire(SpawnRemovalEvent())
        }
    }
}
