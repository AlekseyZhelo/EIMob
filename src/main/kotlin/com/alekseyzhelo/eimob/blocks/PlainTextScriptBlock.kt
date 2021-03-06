package com.alekseyzhelo.eimob.blocks

import com.alekseyzhelo.eimob.MobVisitor
import com.alekseyzhelo.eimob.blocks.Block.Companion.SIG_SCRIPT_PLAIN_TEXT
import com.alekseyzhelo.eimob.decodeMobString
import com.alekseyzhelo.eimob.encodeMobString
import com.alekseyzhelo.eimob.entryHeaderSize
import com.alekseyzhelo.eimob.util.toByteArraySkipHeader
import loggersoft.kotlin.streams.StreamOutput

@ExperimentalUnsignedTypes
class PlainTextScriptBlock(
    bytes: ByteArray
) : ScriptBlock {

    override val signature: UInt = SIG_SCRIPT_PLAIN_TEXT
    override var script: String = bytes.decodeMobString()

    override fun getSize() = entryHeaderSize + script.length

    override fun serialize(out: StreamOutput) {
        with(out) {
            super.serialize(this)
            writeBytes(script.encodeMobString())
        }
    }

    override fun accept(visitor: MobVisitor) {
        visitor.visitScriptBlock(this)
    }

    override fun clone(): PlainTextScriptBlock = PlainTextScriptBlock(toByteArraySkipHeader())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlainTextScriptBlock

        if (script != other.script) return false

        return true
    }

    override fun hashCode(): Int {
        return script.hashCode()
    }
}