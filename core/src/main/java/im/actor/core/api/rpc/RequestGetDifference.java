package im.actor.core.api.rpc;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.runtime.bser.*;
import im.actor.runtime.collections.*;
import static im.actor.runtime.bser.Utils.*;
import im.actor.core.network.parser.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.google.j2objc.annotations.ObjectiveCName;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import im.actor.core.api.*;

public class RequestGetDifference extends Request<ResponseGetDifference> {

    public static final int HEADER = 0xb;
    public static RequestGetDifference fromBytes(byte[] data) throws IOException {
        return Bser.parse(new RequestGetDifference(), data);
    }

    private int seq;
    private byte[] state;

    public RequestGetDifference(int seq, @NotNull byte[] state) {
        this.seq = seq;
        this.state = state;
    }

    public RequestGetDifference() {

    }

    public int getSeq() {
        return this.seq;
    }

    @NotNull
    public byte[] getState() {
        return this.state;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.seq = values.getInt(1);
        this.state = values.getBytes(2);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeInt(1, this.seq);
        if (this.state == null) {
            throw new IOException();
        }
        writer.writeBytes(2, this.state);
    }

    @Override
    public String toString() {
        String res = "rpc GetDifference{";
        res += "seq=" + this.seq;
        res += ", state=" + byteArrayToStringCompact(this.state);
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
