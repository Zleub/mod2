package com.example.examplemod;

import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.awt.*;

public abstract class NoiseStack {
    public NormalNoise noise;
    public ImageDebugger img;

    public NoiseStack(long seed) {
        this.noise =  NormalNoise.create(new SingleThreadedRandomSource(seed), 1, 1);
    }

    public String getName() {
      return "<PLACEHOLDER>";
    };

    public double getX(int x) {
        return (double)x;
    }

    public double getY(int y) {
        return (double)y;
    }

    public double getValue(int x, int y) {
        return (this.noise.getValue(this.getX(x), 12, this.getY(y)) + 1)/ 2;
    }

    public Functor[] getStack() {
        return new Functor[]{};
    }

    public double resolve(int x, int y) {
        double value = this.getValue(x, y);
        for (Functor f : this.getStack()) {
            value = f.apply(value, x, y);
        }
        return value;
    }

    public void makeIMG(int width, int height) {
        int minZ = -height / 2;
        int maxZ = height / 2;
        int minX = -width / 2;
        int maxX = width / 2;

        img = new ImageDebugger(this.getName() + "_" + width + ".png", minX, maxX, minZ, maxZ, this.getStack().length + 1);
        for (int z = minZ; z < maxZ; z += 1) {
            for (int x = minX; x < maxX; x += 1) {
                double value = this.getValue(x, z);
                img.step = 0;
                img.initStep(0, 1);
                img.draw(x, z, value);
                for (Functor f : this.getStack()) {
                    value = f.apply(value, x, z);
                    img.step = img.step + 1;
                    img.initStep(0, 1);
                    img.draw(x, z, value);
                }
            }
        }
        img.end();
    }

    public interface Functor {
        double apply(double d, int x, int y);
    }
}
