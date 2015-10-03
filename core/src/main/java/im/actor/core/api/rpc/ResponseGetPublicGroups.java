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

public class ResponseGetPublicGroups extends Response {

    public static final int HEADER = 0xca;
    public static ResponseGetPublicGroups fromBytes(byte[] data) throws IOException {
        return Bser.parse(new ResponseGetPublicGroups(), data);
    }

    private List<ApiPublicGroup> groups;

    public ResponseGetPublicGroups(@NotNull List<ApiPublicGroup> groups) {
        this.groups = groups;
    }

    public ResponseGetPublicGroups() {

    }

    @NotNull
    public List<ApiPublicGroup> getGroups() {
        return this.groups;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        List<ApiPublicGroup> _groups = new ArrayList<ApiPublicGroup>();
        for (int i = 0; i < values.getRepeatedCount(1); i ++) {
            _groups.add(new ApiPublicGroup());
        }
        this.groups = values.getRepeatedObj(1, _groups);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeRepeatedObj(1, this.groups);
    }

    @Override
    public String toString() {
        String res = "tuple GetPublicGroups{";
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
