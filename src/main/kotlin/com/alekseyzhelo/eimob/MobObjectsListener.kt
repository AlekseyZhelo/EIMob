package com.alekseyzhelo.eimob

import com.alekseyzhelo.eimob.objects.MobMapEntity

@ExperimentalUnsignedTypes
interface MobObjectsListener {
    fun onEntityAdded(entity: MobMapEntity)
    fun onEntityRemoved(entity: MobMapEntity)
    // TODO: onEntityChanged + notifyChange in objects block (or a better place?)
}