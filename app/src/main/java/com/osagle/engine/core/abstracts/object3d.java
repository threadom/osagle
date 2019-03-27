package com.osagle.engine.core.abstracts;

import com.osagle.engine.core.utils.euler;
import com.osagle.engine.core.utils.layers;
import com.osagle.engine.core.utils.matrix4;
import com.osagle.engine.core.utils.quaternion;
import com.osagle.engine.core.utils.vector3;

public class object3d {
    vector3 mPosition = new vector3();
    euler mRotation = new euler();
    quaternion mQuaternion = new quaternion();
    vector3 mScale = new vector3(1,1,1);

    matrix4 mMatrix = new matrix4();
    matrix4 mMatrixWorld = new matrix4();
    layers mLayers = new layers();

    public void onRotationChange() {

    }
    public void onQuaternionChange() {

    }


}
