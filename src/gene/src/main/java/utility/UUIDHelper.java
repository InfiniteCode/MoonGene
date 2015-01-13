//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package utility;

import java.util.UUID;
import java.nio.ByteBuffer;

public class UUIDHelper {

    //Get ID as a byte array from UUID
    static public byte[] getIdAsBytes(UUID uuid)
    {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    //Get ID as a byte array from String
    static public byte[] getIdAsBytes(String uuid)
    {
        return getIdAsBytes(UUID.fromString(uuid));
    }


}

