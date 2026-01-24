package com.kreidev.cbmnfieldlab.util;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

// Copied from Ponder Catnip, decided to just copy instead of requiring ponder as a dependency
public class VecHelper {

    public static Vec3 rotate(Vec3 vec, double deg, Direction.Axis axis) {
        if (deg == 0)
            return vec;
        if (vec == Vec3.ZERO)
            return vec;

        float angle = (float) (deg / 180f * Math.PI);
        double sin = Mth.sin(angle);
        double cos = Mth.cos(angle);
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;

        if (axis == Direction.Axis.X)
            return new Vec3(x, y * cos - z * sin, z * cos + y * sin);
        if (axis == Direction.Axis.Y)
            return new Vec3(x * cos + z * sin, y, z * cos - x * sin);
        if (axis == Direction.Axis.Z)
            return new Vec3(x * cos - y * sin, y * cos + x * sin, z);
        return vec;
    }
}
