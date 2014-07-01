package com.dajie.push.mqtt.encoder;

import com.dajie.push.mqtt.MQTTParseUtils;
import com.dajie.push.mqtt.message.AbstractMessage;
import com.dajie.push.mqtt.message.SubAckMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author wills
 */
class SubAckEncoder extends BaseEncoder<SubAckMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, SubAckMessage message, ByteBuf out) {
        if (message.types().isEmpty()) {
            throw new IllegalArgumentException("Found a suback message with empty topics");
        }

        int variableHeaderSize = 2 + message.types().size();
        ByteBuf buff = chc.alloc().buffer(6 + variableHeaderSize);
        try {
            buff.writeByte(AbstractMessage.SUBACK << 4 );
            buff.writeBytes(MQTTParseUtils.encodeRemainingLength(variableHeaderSize));
            buff.writeShort(message.getMessageID());
            for (AbstractMessage.QOSType c : message.types()) {
                buff.writeByte(c.ordinal());
            }

            out.writeBytes(buff);
        } finally {
            buff.release();
        }
    }
    
}
