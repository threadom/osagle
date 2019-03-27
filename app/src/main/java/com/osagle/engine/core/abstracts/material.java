package com.osagle.engine.core.abstracts;

public abstract class material {
    public int side;
    public static final int doubleSide = 0;
    public static final int frontSide = 1;
    public static final int backSide = 2;

    public com.osagle.engine.core.maps.texturemap texturemap;
    public com.osagle.engine.core.maps.alphamap alphamap;

    public com.osagle.engine.core.utils.color color = new com.osagle.engine.core.utils.color(new int[]{1,1,1});
    public com.osagle.engine.core.utils.color ambient = new com.osagle.engine.core.utils.color(new int[]{1,1,1});
    public com.osagle.engine.core.utils.color specular = new com.osagle.engine.core.utils.color(new int[]{1,1,1});
    public com.osagle.engine.core.utils.color emissive = new com.osagle.engine.core.utils.color(new int[]{1,1,1});

    public com.osagle.engine.core.maps.bumpmap bumpmap;
    public float bumpscale = 0;
    public boolean transparent = false;
    public float shininess = 0;
    public float reflectivity = 0;
    public float opacity = 1;

    public com.osagle.engine.core.utils.texturecube texturecube;
}