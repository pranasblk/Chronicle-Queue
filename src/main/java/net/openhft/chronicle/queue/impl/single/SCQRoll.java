package net.openhft.chronicle.queue.impl.single;

import net.openhft.chronicle.core.annotation.UsedViaReflection;
import net.openhft.chronicle.queue.RollCycle;
import net.openhft.chronicle.wire.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by peter on 22/05/16.
 */
class SCQRoll implements Demarshallable, WriteMarshallable {
    private final int length;
    @Nullable
    private final String format;
    private final long epoch;

    /**
     * used by {@link Demarshallable}
     *
     * @param wire a wire
     */
    @UsedViaReflection
    private SCQRoll(WireIn wire) {
        length = wire.read(RollFields.length).int32();
        format = wire.read(RollFields.format).text();
        epoch = wire.read(RollFields.epoch).int64();
    }

    SCQRoll(@NotNull RollCycle rollCycle, long epoch) {
        this.length = rollCycle.length();
        this.format = rollCycle.format();
        this.epoch = epoch;
    }

    @Override
    public void writeMarshallable(@NotNull WireOut wire) {
        wire.write(RollFields.length).int32(length)
                .write(RollFields.format).text(format)
                .write(RollFields.epoch).int64(epoch);
    }

    /**
     * @return an epoch offset as the number of number of milliseconds since January 1, 1970,
     * 00:00:00 GMT
     */
    public long epoch() {
        return this.epoch;
    }

    enum RollFields implements WireKey {
        length, format, epoch,
    }
}
