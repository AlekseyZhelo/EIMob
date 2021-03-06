package com.alekseyzhelo.eimob.objects

import com.alekseyzhelo.eimob.*
import com.alekseyzhelo.eimob.types.Float3
import com.alekseyzhelo.eimob.types.Float4
import loggersoft.kotlin.streams.StreamInput
import loggersoft.kotlin.streams.StreamOutput

@Suppress("MemberVisibilityCanBePrivate")
@ExperimentalUnsignedTypes
abstract class MobObjectBase : MobMapEntity() {

    var bodyParts: Array<String> = Array(0) { "" }
    open var nPlayer: Byte = (-1).toByte()
        set(value) {
            require(value in -1..31) { "Bad player number: $value" }
            field = value
        }
    var objType: Int = -1
    var modelName: String = ""
    var parentTemplate: String = ""
    var primaryTexture: String = ""
    var secondaryTexture: String = ""
    var unknownStr: String = ""
    var orientation: Float4 = Float4(-1f, -1f, -1f, -1f)
    var isQuestUnit: Boolean = false
    var showShadow: Boolean = false
    var unknownInt: Int = -1
    var questInfo: String = ""
    var bodyConstitution: Float3 = Float3(-1f, -1f, -1f)

    fun getCommonObjectDataSize() = 17 * entryHeaderSize + bodyParts.mobEntrySize() + 1 + 4 + 4 +
            name.length + modelName.length + parentTemplate.length +
            primaryTexture.length + secondaryTexture.length +
            unknownStr.length + 12 + 16 + 1 + 1 + 4 + questInfo.length + 12

    protected fun readCommonObjectData(input: StreamInput) {
        with(input) {
            bodyParts = readMobStringArray(
                SIG_BODY_PARTS, "Failed to read body parts in object block",
                "Unexpected signature in body parts array"
            )
            nPlayer = readMobByte(SIG_N_PLAYER, "Failed to read player number in object block")
            id = readMobInt(SIG_ID, "Failed to read id in object block")
            objType = readMobInt(SIG_OBJ_TYPE, "Failed to read object type in object block")
            name = readMobString(SIG_NAME, "Failed to read name in object block")
            modelName = readMobString(SIG_MODEL_NAME, "Failed to read model name in object block")
            parentTemplate = readMobString(SIG_PARENT_TEMPLATE, "Failed to read parent template in object block")
            primaryTexture = readMobString(SIG_PRIMARY_TEXTURE, "Failed to read primary texture in object block")
            secondaryTexture = readMobString(SIG_SECONDARY_TEXTURE, "Failed to read secondary texture in object block")
            unknownStr = readMobString(SIG_UNKNOWN_STR, "Failed to read unknown string in object block")
            location = readMobFloat3(SIG_LOCATION, "Failed to read id location object block")
            orientation = readMobFloat4(SIG_ORIENTATION, "Failed to read orientation in object block")
            isQuestUnit = readMobBoolean(SIG_IS_QUEST_UNIT, "Failed to read quest unit flag in object block")
            showShadow = readMobBoolean(SIG_SHOW_SHADOW, "Failed to read show shadow flag in object block")
            unknownInt = readMobInt(SIG_UNKNOWN_INT, "Failed to read unknown int in object block")
            questInfo = readMobString(SIG_QUEST_INFO, "Failed to read quest info in object block")
            bodyConstitution = readMobFloat3(SIG_BODY_CONSTITUTION, "Failed to read body constitution in object block")
        }
    }

    protected fun writeCommonObjectData(out: StreamOutput) {
        with(out) {
            writeMobStringArray(SIG_BODY_PARTS, bodyParts)
            writeMobByte(SIG_N_PLAYER, nPlayer)
            writeMobInt(SIG_ID, id)
            writeMobInt(SIG_OBJ_TYPE, objType)
            writeMobString(SIG_NAME, name)
            writeMobString(SIG_MODEL_NAME, modelName)
            writeMobString(SIG_PARENT_TEMPLATE, parentTemplate)
            writeMobString(SIG_PRIMARY_TEXTURE, primaryTexture)
            writeMobString(SIG_SECONDARY_TEXTURE, secondaryTexture)
            writeMobString(SIG_UNKNOWN_STR, unknownStr)
            writeMobFloat3(SIG_LOCATION, this@MobObjectBase.location)
            writeMobFloat4(SIG_ORIENTATION, orientation)
            writeMobBoolean(SIG_IS_QUEST_UNIT, isQuestUnit)
            writeMobBoolean(SIG_SHOW_SHADOW, showShadow)
            writeMobInt(SIG_UNKNOWN_INT, unknownInt)
            writeMobString(SIG_QUEST_INFO, questInfo)
            writeMobFloat3(SIG_BODY_CONSTITUTION, bodyConstitution)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as MobObjectBase

        if (!bodyParts.contentEquals(other.bodyParts)) return false
        if (nPlayer != other.nPlayer) return false
        if (objType != other.objType) return false
        if (modelName != other.modelName) return false
        if (parentTemplate != other.parentTemplate) return false
        if (primaryTexture != other.primaryTexture) return false
        if (secondaryTexture != other.secondaryTexture) return false
        if (unknownStr != other.unknownStr) return false
        if (orientation != other.orientation) return false
        if (isQuestUnit != other.isQuestUnit) return false
        if (showShadow != other.showShadow) return false
        if (unknownInt != other.unknownInt) return false
        if (questInfo != other.questInfo) return false
        if (bodyConstitution != other.bodyConstitution) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + bodyParts.contentHashCode()
        result = 31 * result + nPlayer
        result = 31 * result + objType
        result = 31 * result + modelName.hashCode()
        result = 31 * result + parentTemplate.hashCode()
        result = 31 * result + primaryTexture.hashCode()
        result = 31 * result + secondaryTexture.hashCode()
        result = 31 * result + unknownStr.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + isQuestUnit.hashCode()
        result = 31 * result + showShadow.hashCode()
        result = 31 * result + unknownInt
        result = 31 * result + questInfo.hashCode()
        result = 31 * result + bodyConstitution.hashCode()
        return result
    }

    companion object {
        const val SIG_BODY_PARTS = 0x0000B00Du
        const val SIG_N_PLAYER = 0x0000B011u
        const val SIG_ID = 0x0000B002u
        const val SIG_OBJ_TYPE = 0x0000B003u
        const val SIG_NAME = 0x0000B004u
        const val SIG_MODEL_NAME = 0x0000B006u
        const val SIG_PARENT_TEMPLATE = 0x0000B00Eu
        const val SIG_PRIMARY_TEXTURE = 0x0000B007u
        const val SIG_SECONDARY_TEXTURE = 0x0000B008u
        const val SIG_UNKNOWN_STR = 0x0000B00Fu
        const val SIG_LOCATION = 0x0000B009u
        const val SIG_ORIENTATION = 0x0000B00Au
        const val SIG_IS_QUEST_UNIT = 0x0000B013u
        const val SIG_SHOW_SHADOW = 0x0000B014u
        const val SIG_UNKNOWN_INT = 0x0000B012u
        const val SIG_QUEST_INFO = 0x0000B016u
        const val SIG_BODY_CONSTITUTION = 0x0000B00Cu
    }
}