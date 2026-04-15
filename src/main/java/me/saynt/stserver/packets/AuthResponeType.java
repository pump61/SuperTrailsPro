package me.saynt.stserver.packets;

import java.io.Serializable;

public enum AuthResponeType implements Serializable {
   Success,
   Fail,
   Error,
   Maintenance;
}
