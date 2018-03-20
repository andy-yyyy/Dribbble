package com.andy.dribbble;

import android.graphics.Matrix;

/**
 * Matrix是一个由9个float值构成的3*3矩阵：
 * [MSCALE_X, MSKEW_X, MTRANS_X]
 * [MSKEW_Y, MSCALE_Y, MTRANS_Y]
 * [MPERSP_0, MPERSP_1, MPERSP_2]
 *
 *
 * Created by lixn on 2018/3/20.
 */

public class MatrixUtil {

    public static final int SCALE_X_INDEX = 0;
    public static final int SKEW_X_INDEX = 1;
    public static final int TRANS_X_INDEX = 2;
    public static final int SKEW_Y_INDEX = 3;
    public static final int SCALE_Y_INDEX = 4;
    public static final int TRANS_Y_INDEX = 5;
    public static final int PERSP_0_INDEX = 6;
    public static final int PERSP_1_INDEX = 7;
    public static final int PERSP_2_INDEX = 8;


    public static float getMatrixScaleX(Matrix m) {
        return getMatrixValues(m)[SCALE_X_INDEX];
    }

    public static float getMatrixScaleY(Matrix m) {
        return getMatrixValues(m)[SCALE_Y_INDEX];
    }

    public static float getMatrixTranslateX(Matrix m) {
        return getMatrixValues(m)[TRANS_X_INDEX];
    }

    public static float getMatrixTranslateY(Matrix m) {
        return getMatrixValues(m)[TRANS_Y_INDEX];
    }

    private static float[] getMatrixValues(Matrix m) {
        float[] v = new float[9];
        m.getValues(v);
        return v;
    }

    public static void print(Matrix m) {
        System.out.println(toString(m));
    }

    public static String toString(Matrix m) {
        float[] v = getMatrixValues(m);
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(v[0]).append(", ").append(v[1]).append(", ").append(v[2]).append("}\n")
                .append("{").append(v[3]).append(", ").append(v[4]).append(", ").append(v[5]).append("}\n")
                .append("{").append(v[6]).append(", ").append(7).append(", ").append(v[8]).append("}\n");
        return sb.toString();
    }

}
