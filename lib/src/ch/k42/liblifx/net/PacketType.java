package ch.k42.liblifx.net;

import ch.k42.liblifx.minions.Uint16;

/**
 * Created by Thomas on 30.01.14.
 */
public enum PacketType {
    GET_PAN_GATEWAY(0x02),
    R_PAN_GATEWAY(0x03),
    GET_POWER_STATE(0x14),
    SET_POWER_STATE(0x15),
    R_POWER_STATE(0x16),
    GET_WIFI_INFO(0x10),
    R_WIFI_INFO(0x11),
    // wifi stuff
    GET_BULB_LABEL(0x17),
    SET_BULB_LABEL(0x18),
    R_BULB_LABEL(0x19),
    GET_TAGS(0x1a),
    SET_TAGS(0x1b),
    R_TAGS(0x1c),

    GET_VERSION(0x20),
    REBOOT(0x26),
    // Set/get tag labels
    GET_LIGHT_STATE(0x65),
    SET_LIGHT_STATE(0x66),
    SET_WAVEFORM(0x67),
    SET_DIM_ABSOLUTE(0x68),
    SET_DIM_RELATIVE(0x69),
    R_LIGHT_STATUS(0x6b),

    GET_TIME(0x04),
    SET_TIME(0x05),
    R_TIME_STATE(0x06),

    // Diagnostics
    BLA(123);
    PacketType(int type){
        this.type = new Uint16(type);
    }
    public final Uint16 type;
}
