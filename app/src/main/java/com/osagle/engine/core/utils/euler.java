/**
 * from : https://github.com/mrdoob/three.js/blob/master/src/math/Euler.js
 */

package com.osagle.engine.core.utils;

import com.osagle.engine.debug;

import java.lang.reflect.Method;

import static com.osagle.engine.core.utils.utils.getKey;

public class euler {
    double mX, mY, mZ;
    String mOrder;
    Method mCallback;

    public void euler() {
        mX = mY = mZ = 0;
        mOrder = euler.DefaultOrder;
    }

    public void euler(double pX, double pY, double pZ, String pOrder) {
        mX = pX;
        mY = pY;
        mZ = pZ;
        mOrder = pOrder;
    }

    public static String[] RotationOrders = { "XYZ", "YZX", "ZXY", "XZY", "YXZ", "ZYX" };
    public static final String DefaultOrder = "XYZ";

    public double getX() { return mX; }
    public void setX(Double pX) { mX = pX; this.onChangeCallback(); }

    public double getY() { return mY; }
    public void setY(Double pY) { mX = pY; this.onChangeCallback(); }

    public double getZ() { return mZ; }
    public void setZ(Double pZ) { mX = pZ; this.onChangeCallback(); }

    public String getOrder() { return mOrder; }
    public void setOrder(String pOrder) { mOrder = pOrder; this.onChangeCallback(); }

    public euler setEuler(double pX, double pY, double pZ, String pOrder) {
        mX = pX;
        mY = pY;
        mZ = pZ;
        mOrder = pOrder;

        this.onChangeCallback();
        return this;
    }

    public euler copy(euler pEuler) {
        this.mX = pEuler.getX();
        this.mY = pEuler.getY();
        this.mZ = pEuler.getZ();
        this.mOrder = pEuler.getOrder();

        this.onChangeCallback();
        return this;
    }

    public euler setFromRotationMatrix(matrix4 mMatrix, String pOrder, boolean pUpdate) {
        double[] te = mMatrix.elements();
        double m11 = te[0], m12 = te[4], m13 = te[8];
        double m21 = te[1], m22 = te[5], m23 = te[9];
        double m31 = te[2], m32 = te[6], m33 = te[10];

        if (pOrder == null) { pOrder = mOrder; }

        if (mOrder == "XYZ") {
            mY = Math.asin(utils.clamp(m13, -1, 1));
            if (Math.abs(m13) < 0.99999) {
                mX = Math.atan2(-m23, m33);
                mZ = Math.atan2(-m12, m11);
            } else {
                mX = Math.atan2(m32, m22);
                mZ = 0;
            }
        }
        else if (mOrder == "YXZ") {
            mX = Math.asin(-utils.clamp(m23, -1, 1));
            if (Math.abs(m23) < 0.99999) {
                mY = Math.atan2(m13, m33);
                mZ = Math.atan2(m21, m22);
            } else {
                mY = Math.atan2(-m31, m11);
                mZ = 0;
            }
        }
        else if (mOrder == "ZXY") {
            mX = Math.asin(utils.clamp(m32, -1, 1));
            if (Math.abs(m32) < 0.99999) {
                mY = Math.atan2(-m31, m33);
                mZ = Math.atan2(-m12, m22);
            } else {
                mY = 0;
                mZ = Math.atan2(m21, m11);
            }
        }
        else if (mOrder == "ZYX") {
            mY = Math.asin(-utils.clamp(m31, -1, 1));
            if (Math.abs(m31) < 0.99999) {
                mY = Math.atan2(m32, m33);
                mZ = Math.atan2(m21, m11);
            } else {
                mY = 0;
                mZ = Math.atan2(-m12, m22);
            }
        }
        else if (mOrder == "YZX") {
            mZ = Math.asin(utils.clamp(m21, -1, 1));
            if (Math.abs(m21) < 0.99999) {
                mY = Math.atan2(-m23, m22);
                mZ = Math.atan2(-m31, m11);
            } else {
                mY = 0;
                mZ = Math.atan2(m13, m33);
            }
        }
        else if (mOrder == "XZY") {
            mZ = Math.asin(-utils.clamp(m12, -1, 1));
            if (Math.abs(m21) < 0.99999) {
                mX = Math.atan2(m32, m22);
                mY = Math.atan2(m13, m11);
            } else {
                mX = 0;
                mY = Math.atan2(-m23, m33);
            }
        }
        else {
            debug.log("OSAGLE.euler", ".setFromRotationMatrix() given unsupported order: " + mOrder);
        }

        mOrder = pOrder;

        if (pUpdate) { this.onChangeCallback(); }

        return this;
    }

    public euler setFromQuaternion(quaternion pQuaternion, String pOrder) {
        setFromQuaternion(pQuaternion, pOrder, true);
    }
    public euler setFromQuaternion(quaternion pQuaternion, String pOrder, boolean pUpdate) {
        matrix4 aMatrix = new matrix4();
        aMatrix.makeRotationFromQuaternion(pQuaternion);
        return this.setFromRotationMatrix(aMatrix, pOrder, pUpdate);
    }

    public euler setFromVector3(vector3 pVector, String pOrder) {
        return this.setEuler(pVector.getX(), pVector.getY(), pVector.getZ(), pOrder);
    }

    public euler reorder(String pNewOrder) {
        quaternion aQuaternion = new quaternion();
        aQuaternion.setFromEuler(this);
        return this.setFromQuaternion(aQuaternion, pNewOrder);
    }

    public boolean equals(euler pEuler) {
        return ( pEuler.getX() == this.mX ) && ( pEuler.getY() == this.mY ) && ( pEuler.getZ() == this.mZ ) && ( pEuler.getOrder() == this.mOrder );
    }

    public euler fromArray(double[] pArray) {
        this.mX = pArray[0];
        this.mY = pArray[1];
        this.mZ = pArray[2];
        if (pArray.length > 3) { this.mOrder = euler.RotationOrders[(int) pArray[3]]; }

        this.onChangeCallback();

        return this;
    }

    public double[] toArray() {
        return toArray(new double[]{}, 0);
    }
    public double[] toArray(double[] pArray) {
        return toArray(pArray, 0);
    }
    public double[] toArray(double[] pArray, int offset) {
        pArray[ offset ] = this.mX;
        pArray[ offset + 1 ] = this.mY;
        pArray[ offset + 2 ] = this.mZ;
        pArray[ offset + 3 ] = (double) getKey(euler.RotationOrders, this.mOrder);

        return pArray;
    }

    public vector3 toVector3() {
        return new vector3(this.mX, this.mY, this.mZ);
    }

    public setOnChange(Method pCallBack) {
        mCallback = pCallBack;
    }

    public void onChangeCallback() {
        try {
            mCallback.invoke(null,null);
        }
        catch (Exception e) {
            debug.log("OSAGLE.euler", "onChangeCallback.Exception : " + e.toString());
        }
    }
}
