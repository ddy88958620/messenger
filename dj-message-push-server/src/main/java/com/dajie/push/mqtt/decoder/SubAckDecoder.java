package com.dajie.push.mqtt.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import com.dajie.push.mqtt.message.AbstractMessage;
import com.dajie.push.mqtt.message.SubAckMessage;

/**
 *
 * @author wills
 */
class SubAckDecoder extends BaseDecoder {

    @Override
    void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //Common decoding part
        in.resetReaderIndex();
        SubAckMessage message = new SubAckMessage();
        if (!decodeCommonHeader(message, in)) {
            in.resetReaderIndex();
            return;
        }
        int remainingLength = message.getRemainingLength();
        
        //MessageID
        message.setMessageID(in.readUnsignedShort());
        remainingLength -= 2;
        
        //Qos array
        if (in.readableBytes() < remainingLength ) {
            in.resetReaderIndex();
            return;
        }
        for (int i = 0; i < remainingLength; i++) {
            byte qos = in.readByte();
            message.addType(AbstractMessage.QOSType.values()[qos]);
        }
        
        out.add(message);
    }
    
}
