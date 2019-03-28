/**
 * from : https://github.com/mrdoob/three.js/blob/master/src/math/Quaternion.js
 */

package com.osagle.engine.core.utils;

import com.osagle.engine.debug;

import java.lang.reflect.Method;

public class quaternion {
    double mX, mY, mZ;
    double mW;
    Method mCallback;

    public quaternion() {
        this(0,0,0,1);
    }
    public quaternion(double pX, double pY, double pZ, double pW) {
        mX = pX;
        mY = pY;
        mZ = pZ;
        mW = pW;
    }

    public quaternion slerp(quaternion pQa, quaternion pQb, quaternion pQm, double pT) {
        return pQm.copy(pQa).slerp(pQb, pT);
    }

    public double[] slerpFlat(double[] pDst, int pDstOffset, double[] pSrc0, int pSrcOffset0, double[] pSrc1, int pSrcOffset1, double pT) {
        double aX0 = pSrc0[pSrcOffset0 + 0];
        double aY0 = pSrc0[pSrcOffset0 + 1];
        double aZ0 = pSrc0[pSrcOffset0 + 2];
        double aW0 = pSrc0[pSrcOffset0 + 3];

        double aX1 = pSrc1[pSrcOffset1 + 0];
        double aY1 = pSrc1[pSrcOffset1 + 1];
        double aZ1 = pSrc1[pSrcOffset1 + 2];
        double aW1 = pSrc1[pSrcOffset1 + 3];

        if (aW0 != aW1 || aX0 != aX1 || aY0 != aY1 || aZ0 != aZ1) {
            double aS = 1 - pT;
            double aCos = aX0 * aX1 + aY0 * aY1 + aZ0 * aZ1 + aW0 * aW1;
            double aDir = ( aCos >= 0 ? 1 : - 1 );
            double aSqrSin = 1 - aCos * aCos;
            if ( aSqrSin > number.EPSILON ) {
                double sin = Math.sqrt( aSqrSin ), len = Math.atan2( sin, aCos * aDir );
                aS = Math.sin( aS * len ) / sin;
                pT = Math.sin( pT * len ) / sin;

            }
            double aTDir = pT * aDir;
            aX0 = aX0 * aS + aX1 * aTDir;
            aY0 = aY0 * aS + aY1 * aTDir;
            aZ0 = aZ0 * aS + aZ1 * aTDir;
            aW0 = aW0 * aS + aW1 * aTDir;
            if (aS == 1 - pT) {
                double aF = 1 / Math.sqrt(aX0 * aX0 + aY0 * aY0 + aZ0 * aZ0 + aW0 * aW0);
                aX0 *= aF;
                aY0 *= aF;
                aZ0 *= aF;
                aW0 *= aF;
            }
        }

        pDst[pDstOffset] = aX0;
        pDst[pDstOffset + 1] = aY0;
        pDst[pDstOffset + 2] = aZ0;
        pDst[pDstOffset + 3] = aW0;

        return pDst;
    }

    public double getX() { return mX; }
    public void setX(double pX) { mX = pX; this.onChangeCallback(); }

    public double getY() { return mY; }
    public void setY(double pY) { mY = pY; this.onChangeCallback(); }

    public double getZ() { return mZ; }
    public void setZ(double pZ) { mZ = pZ; this.onChangeCallback(); }

    public double getW() { return mW; }
    public void setW(double pW) { mW = pW; this.onChangeCallback(); }

    public quaternion setQuaternion(double pX, double pY, double pZ, double pW) {
        this.mX = pX;
        this.mY = pY;
        this.mZ = pZ;
        this.mW = pW;

        this.onChangeCallback();
        return this;
    }
    public quaternion clone() {
        return new quaternion(this.mX,this.mY,this.mZ,this.mW);
    }

    public quaternion copy(quaternion pQuaternion) {
        this.mX = pQuaternion.getX();
        this.mY = pQuaternion.getY();
        this.mZ = pQuaternion.getZ();
        this.mW = pQuaternion.getW();

        this.onChangeCallback();
        return this;
    }
    public quaternion setFromEuler(euler pEuler) {
        return this.setFromEuler(pEuler, false);
    }
    public quaternion setFromEuler(euler pEuler, boolean update) {
        double aX = pEuler.getX();
        double aY = pEuler.getY();
        double aZ = pEuler.getZ();
        String aOrder = pEuler.getOrder();

        double aC1 = Math.cos(aX / 2);
        double aC2 = Math.cos(aY / 2);
        double aC3 = Math.cos(aZ / 2);

        double aS1 = Math.sin(aX / 2);
        double aS2 = Math.sin(aY / 2);
        double aS3 = Math.sin(aZ / 2);

        if (aOrder == "XYZ") {
            mX = aS1 * aC2 * aC3 + aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 - aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 + aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 - aS1 * aS2 * aS3;
        }
        else if (aOrder == "YXZ") {
            mX = aS1 * aC2 * aC3 + aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 - aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 - aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 + aS1 * aS2 * aS3;
        }
        else if (aOrder == "ZXY") {
            mX = aS1 * aC2 * aC3 - aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 + aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 + aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 - aS1 * aS2 * aS3;
        }
        else if (aOrder == "ZYX") {
            mX = aS1 * aC2 * aC3 - aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 + aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 - aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 + aS1 * aS2 * aS3;
        }
        else if (aOrder == "YZX") {
            mX = aS1 * aC2 * aC3 + aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 + aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 - aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 - aS1 * aS2 * aS3;
        }
        else if (aOrder == "XZY") {
            mX = aS1 * aC2 * aC3 - aC1 * aS2 * aS3;
            mY = aC1 * aS2 * aC3 - aS1 * aC2 * aS3;
            mZ = aC1 * aC2 * aS3 + aS1 * aS2 * aC3;
            mW = aC1 * aC2 * aC3 + aS1 * aS2 * aS3;
        }

        if (update) { this.onChangeCallback(); }
        return this;
    }
    public quaternion setFromAxisAngle(vector3 pAxis, double pAngle) {
        double aHalfAngle = pAngle / 2;
        double aS = Math.sin( aHalfAngle );

        mX = pAxis.getX() * aS;
        mY = pAxis.getY() * aS;
        mZ = pAxis.getZ() * aS;
        mW = Math.cos( aHalfAngle );

        this.onChangeCallback();
        return this;
    }
    public quaternion setFromRotationMAtrix(matrix4 mMatrix) {
        double[] te = mMatrix.elements();
        double m11 = te[0], m12 = te[4], m13 = te[8];
        double m21 = te[1], m22 = te[5], m23 = te[9];
        double m31 = te[2], m32 = te[6], m33 = te[10];

        double trace = m11 + m22 + m33;
        double aS;

        if (trace > 0) {
            aS = 0.5 / Math.sqrt(trace + 1.0);
            mW = 0.25 / aS;
            mX = ( m32 - m23 ) * aS;
            mY = ( m13 - m31 ) * aS;
            mZ = ( m21 - m12 ) * aS;
        } else if (m11 > m22 && m11 > m33) {
            aS = 2.0 / Math.sqrt(1.0 + m11 - m22 - m33);
            mW = ( m32 - m23 ) * aS;
            mX = 0.25 / aS;
            mY = ( m12 + m21 ) * aS;
            mZ = ( m13 + m31 ) * aS;
        } else if (m22 > m33) {
            aS = 2.0 / Math.sqrt(1.0 + m22 - m11 - m33);
            mW = ( m13 - m31 ) * aS;
            mX = ( m12 - m21 ) * aS;
            mY = 0.25 / aS;
            mZ = ( m23 + m32 ) * aS;
        } else {
            aS = 2.0 / Math.sqrt(1.0 + m33 - m11 - m22);
            mW = ( m21 - m12 ) * aS;
            mX = ( m13 + m31 ) * aS;
            mZ = ( m23 + m32 ) * aS;
            mY = 0.25 / aS;
        }

        this.onChangeCallback();
        return this;
    }
    public quaternion setFromUnitVectors(vector3 aVFrom, vector3 vTo) {
        double EPS = 0.000001;
        double r = aVFrom.dot(vTo) + 1;

        if ( r < EPS ) {
            r = 0;
            if ( Math.abs( aVFrom.getX() ) > Math.abs( aVFrom.getZ() ) ) {
                mX = - aVFrom.getY();
                mY = aVFrom.getX();
                mZ = 0;
                mW = r;
            } else {
                mX = 0;
                mY = - aVFrom.getZ();
                mZ = aVFrom.getY();
                mW = r;
            }
        } else {
            mX = aVFrom.getY() * vTo.getZ() - aVFrom.getZ() * vTo.getY();
            mY = aVFrom.getZ() * vTo.getX() - aVFrom.getX() * vTo.getZ();
            mZ = aVFrom.getX() * vTo.getY() - aVFrom.getY() * vTo.getX();
            mW = r;
        }

        return this.normalize();
    }
    public double angleTo(quaternion pQuaternion) {
        return 2 * Math.acos( Math.abs( utils.clamp( this.dot(pQuaternion), - 1, 1 ) ) );
    }
    public quaternion rotateTowards(quaternion pQuaternion, double pStep) {
        double aAngle = this.angleTo(pQuaternion);
        if ( aAngle == 0 ) return this;
        double aT = Math.min( 1, pStep / aAngle );
        this.slerp(pQuaternion, aT);
        return this;
    }
    public quaternion inverse() {
        return this.conjugate();
    }
    public quaternion conjugate() {
        mX *= -1;
        mY *= -1;
        mZ *= -1;

        this.onChangeCallback();
        return this;
    }
    public double dot(quaternion pQuaternion) {
        return mX * pQuaternion.getX() + mY * pQuaternion.getY() + mZ * pQuaternion.getZ() + mW * pQuaternion.getW();
    }
    public double lengthSq() {
        return mX * mX + mY * mY + mZ * mZ + mW * mW;
    }
    public double length() {
        return Math.sqrt(mX * mX + mY * mY + mZ * mZ + mW * mW);
    }
    public quaternion normalize() {
        double aL = this.length();

        if ( aL == 0 ) {
            mX = 0;
            mY = 0;
            mZ = 0;
            mW = 1;
        } else {
            aL = 1 / aL;
            mX = mX * aL;
            mY = mY * aL;
            mZ = mZ * aL;
            mW = mW * aL;
        }

        this.onChangeCallback();
        return this;
    }
    public quaternion multiply(quaternion pQ1, quaternion pQ2) {
        if (pQ2 == null) {
            return this.multiplyQuaternions(pQ1, pQ2);
        }
        return this.multiplyQuaternions(this,pQ1);

    }
    public quaternion premultiply(quaternion pQ) {
        return this.multiplyQuaternions(pQ , this);
    }
    public quaternion multiplyQuaternions(quaternion pA, quaternion pB) {
        double QAX = pA.getX();
        double QAY = pA.getY();
        double QAZ = pA.getZ();
        double QAW = pA.getW();
        double QBX = pB.getX();
        double QBY = pB.getY();
        double QBZ = pB.getZ();
        double QBW = pB.getW();

        mX = QAX * QBW + QAW * QBX + QAY * QBZ - QAZ * QBY;
        mY = QAY * QBW + QAW * QBY + QAZ * QBX - QAX * QBZ;
        mZ = QAZ * QBW + QAW * QBZ + QAX * QBY - QAY * QBX;
        mW = QAW * QBW - QAX * QBX - QAY * QBY - QAZ * QBZ;

        this.onChangeCallback();
        return this;
    }
    public quaternion slerp(quaternion pQb, double pT) {
        if ( pT == 0 ) return this;
        if ( pT == 1 ) return this.copy( pQb );

        double x = mX, y = mY, z = mZ, w = mW;

        double cosHalfTheta = w * pQb.getW() + x * pQb.getX() + y * pQb.getY() + z * pQb.getZ();
        if ( cosHalfTheta < 0 ) {
            mW = - pQb.getW();
            mX = - pQb.getX();
            mY = - pQb.getY();
            mZ = - pQb.getZ();
            cosHalfTheta = - cosHalfTheta;
        } else {
            this.copy( pQb );
        }
        if ( cosHalfTheta >= 1.0 ) {
            mW = w;
            mX = x;
            mY = y;
            mZ = z;
            return this;
        }

        double sqrSinHalfTheta = 1.0 - cosHalfTheta * cosHalfTheta;
        if ( sqrSinHalfTheta <= number.EPSILON ) {
            double s = 1 - pT;
            mW = s * w + pT * mW;
            mX = s * x + pT * mX;
            mY = s * y + pT * mY;
            mZ = s * z + pT * mZ;
            return this.normalize();
        }

        double sinHalfTheta = Math.sqrt( sqrSinHalfTheta );
        double halfTheta = Math.atan2( sinHalfTheta, cosHalfTheta );
        double ratioA = Math.sin( ( 1 - pT ) * halfTheta ) / sinHalfTheta, ratioB = Math.sin( pT * halfTheta ) / sinHalfTheta;

        mW = ( w * ratioA + mW * ratioB );
        mX = ( x * ratioA + mX * ratioB );
        mY = ( y * ratioA + mY * ratioB );
        mZ = ( z * ratioA + mZ * ratioB );

        this.onChangeCallback();
        return this;
    }
    public boolean equals(quaternion pQuaternion) {
        return ( pQuaternion.getX() == mX ) && ( pQuaternion.getY() == mY ) && ( pQuaternion.getZ() == mZ ) && ( pQuaternion.getW() == mW );
    }
    public void fromArray(double[] pArray) {
        this.fromArray(pArray, 0);
    }
    public quaternion fromArray(double[] pArray, int pOffset) {
        mX = pArray[ pOffset ];
        mY = pArray[ pOffset + 1 ];
        mZ = pArray[ pOffset + 2 ];
        mW = pArray[ pOffset + 3 ];

        this.onChangeCallback();
        return this;
    }
    public quaternion toArray(double[] pArray, int pOffset) {
        pArray[ pOffset ] = mX;
        pArray[ pOffset + 1 ] = mY;
        pArray[ pOffset + 2 ] = mZ;
        pArray[ pOffset + 3 ] = mW;

        return this;
    }
    public void setOnChange(Method pCallBack) {
        mCallback = pCallBack;
    }

    public void onChangeCallback() {
        try {
            mCallback.invoke(null,null);
        }
        catch (Exception e) {
            debug.log("OSAGLE.quaternion", "onChangeCallback.Exception : " + e.toString());
        }
    }
}
