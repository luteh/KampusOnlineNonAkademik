package com.luteh.kampusonlinenonakademik.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PermissionHelper {

    /** The {@link Activity} */
    private Activity mActivity;

    /** The {@link Context} */
    private Context mContext;

    /** The {@link List} of permissions */
    private List<String> mPermissions;

    /** The {@link List} of permission information */
    private List<String> mPermissionInfos;

    /**
     * The request code. When unspecified,
     * the permission helper will generate a random number (1-100).
     */
    private int mRequestCode = -1;

    /** The positive button string for the permission info dialog */
    private String mPositiveButtonString;

    /** The negative button string for the permission info dialog */
    private String mNegativeButtonString;

    /** The listener for permission requests */
    private OnPermissionCheckedListener mPermissionCheckedListener;

    /** The {@link List} of denied permissions */
    private List<String> mDeniedPermissions;

    /** The {@link List} of denied permission information */
    private List<String> mDeniedPermissionInfos;

    /** The constructor for this class */
    public PermissionHelper(Activity activity) {
        mActivity = activity;

        mDeniedPermissions = new ArrayList<>();
        mDeniedPermissionInfos = new ArrayList<>();
        initDefaultValues();
    }



    /** The constructor for this class */
    public PermissionHelper(Builder builder) {
        mActivity = builder.getActivity();
        mPermissions = builder.getPermissions();
        mPermissionInfos = builder.getPermissionInfos();
        mRequestCode = builder.getRequestCode();
        mPositiveButtonString = builder.getPositiveButtonString();
        mNegativeButtonString = builder.getNegativeButtonString();
        mPermissionCheckedListener = builder.getPermissionCheckedListener();

        mDeniedPermissions = new ArrayList<>();
        mDeniedPermissionInfos = new ArrayList<>();
        initDefaultValues();
    }

    /** Initialize the default values for the helper */
    private void initDefaultValues() {
        if (mPositiveButtonString == null) {
            mPositiveButtonString = mActivity.getString(android.R.string.ok);
        }

        if (mNegativeButtonString == null) {
            mNegativeButtonString = mActivity.getString(android.R.string.cancel);
        }

        if (mRequestCode == -1) {
            // Generate a random request code (1-100) if request code is unspecified
            mRequestCode = new Random().nextInt(100) + 1;
        }
    }

    /** Get the specified permissions */
    public final List<String> getPermissions() {
        return mPermissions;
    }

    /** Set the permissions for this class */
    public final void setPermissions(String ... permissions) {
        mPermissions = Arrays.asList(permissions);
    }

    /** Set the permissions for this class */
    public final void setPermissions(List<String> permissions) {
        mPermissions = permissions;
    }

    /** Get the specified permission information */
    public final List<String> getPermissionInfos() {
        return mPermissionInfos;
    }

    /** Set the permission information for this class */
    public final void setPermissionInfos(String ... permissionInfos) {
        mPermissionInfos = Arrays.asList(permissionInfos);
    }

    /** Set the permission information for this class */
    public final void setPermissionInfos(List<String> permissionInfos) {
        mPermissionInfos = permissionInfos;
    }

    /** Set the request code */
    public final void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }

    /** Set the positive button string text */
    public final void setPositiveButtonString(int stringRes) {
        setPositiveButtonString(mActivity.getString(stringRes));
    }

    /** Set the positive button string text */
    public final void setPositiveButtonString(String string) {
        mPositiveButtonString = string;
    }

    /** Set the negative button string text */
    public final void setNegativeButtonString(int stringRes) {
        setNegativeButtonString(mActivity.getString(stringRes));
    }

    /** Set the negative button string text */
    public final void setNegativeButtonString(String string) {
        mNegativeButtonString = string;
    }

    /** Set the listener for permission requests */
    public final void setOnPermissionCheckedListener(OnPermissionCheckedListener listener) {
        mPermissionCheckedListener = listener;
    }

    /** Call this method to determine if the specified permissions have been granted or denied */
    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == mRequestCode) {
            if (isPermissionAllowed()) {
                // All permission is allowed
                if (mPermissionCheckedListener != null) mPermissionCheckedListener.onPermissionGranted(false);
            } else {
                // Some permission is still denied
                if (mPermissionCheckedListener != null) mPermissionCheckedListener.onPermissionDenied();
            }
        }
    }

    /** Request all permission access */
    public final void requestPermission() {
        if (isPermissionAllowed()) {
            // All permission is already allowed
            if (mPermissionCheckedListener != null) mPermissionCheckedListener.onPermissionGranted(true);
        } else {
            // Some permission is denied, request the permissions
            if (!mDeniedPermissionInfos.isEmpty()) {
                if (mDeniedPermissions.size() == 1) {
                    requestPermission(mDeniedPermissions.get(0), mDeniedPermissionInfos.get(0));
                } else {
                    // TODO: 7/21/2016 Implement multiple information for each permissions
                    requestPermissions(mDeniedPermissions);
                }
            } else {
                if (mDeniedPermissions.size() == 1) {
                    requestPermission(mDeniedPermissions.get(0));
                } else {
                    requestPermissions(mDeniedPermissions);
                }
            }
        }
    }

    /**
     * Determines if app has full specified permission access.
     * @return true if <strong>ALL</strong> permissions are allowed, otherwise false
     */
    private boolean isPermissionAllowed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissions == null) {
                Log.e(getClass().getSimpleName(), "Permission list is null, make sure the permissions has been set!");
                return false;
            } else {
                boolean isInfoValid = false;
                if (mPermissionInfos != null) {
                    if (mPermissions.size() == mPermissionInfos.size()) {
                        isInfoValid = true;
                    } else {
                        Log.w(getClass().getSimpleName(), "Permission info list doesn't have the same size as permission list. Not showing permission information dialog.");
                    }
                }

                mDeniedPermissions = new ArrayList<>();
                mDeniedPermissionInfos = new ArrayList<>();

                boolean isAllPermissionAllowed = true;
                int size = mPermissions.size();
                for (int i = 0; i < size; i++) {
                    if (!hasPermission(mPermissions.get(i))) {
                        isAllPermissionAllowed = false;

                        // Add the permission
                        if (!mDeniedPermissions.contains(mPermissions.get(i))) {
                            mDeniedPermissions.add(mPermissions.get(i));
                        }

                        // Add the permission information if specified correctly
                        if (isInfoValid && !mDeniedPermissionInfos.contains(mPermissionInfos.get(i))) {
                            mDeniedPermissionInfos.add(mPermissionInfos.get(i));
                        }
                    }
                }

                return isAllPermissionAllowed;
            }
        } else {
            // Treat all permission request as a granted for API<23
            Log.i(getClass().getSimpleName(), "Requesting permission for API<23, treating it as a success");
            return true;
        }
    }

    /**
     * Checks if application has a specified permission.
     * @param permission the specified permission
     * @return true if permission is granted
     */
    private boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (mActivity.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checks if application has the specified permissions.
     * @param permissions the permission list
     * @return true if <strong>ALL</strong> permissions are granted
     */
    private boolean hasPermission(String ... permissions) {
        return hasPermission(Arrays.asList(permissions));
    }

    /**
     * Checks if application has the specified permissions.
     * @param permissions the permission list
     * @return true if <strong>ALL</strong> permissions are granted
     */
    private boolean hasPermission(List<String> permissions) {
        boolean isAllGranted = true;

        int size = permissions.size();
        String permission;
        for (int i = 0; i < size; i++) {
            permission = permissions.get(i);
            if (!hasPermission(permission)) {
                if (isAllGranted) isAllGranted = false;
            }
        }

        return isAllGranted;
    }

    /**
     * Request a permission access.
     * @param permission the specified permission
     */
    private void requestPermission(String permission) {
        requestPermission(permission, null);
    }

    /**
     * Request a permission access while displaying an information.
     * @param permission the specified permission
     * @param permissionInfo the information for the specified permission
     */
    private void requestPermission(String permission, String permissionInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermission(permission)) {
                if (permissionInfo != null && !permissionInfo.isEmpty()) {
                    // Check if app has requested this permission previously and the user denied the request
                    if (mActivity.shouldShowRequestPermissionRationale(permission)) {
                        // Display the explanation
                        Dialog permissionDetailDialog = initPermissionDetailDialog(permission, permissionInfo);
                        permissionDetailDialog.show();
                    } else {
                        // Don't display any explanation, request immediately
                        mActivity.requestPermissions(
                                new String[]{permission},
                                mRequestCode);
                    }
                } else {
                    // Don't display any explanation, request immediately
                    mActivity.requestPermissions(
                            new String[]{permission},
                            mRequestCode);
                }
            }
        } else {
            // Treat the permission request as a success for API<23
            Log.i(getClass().getSimpleName(), "Requesting permission for API<23, treating it as a success");
            if (mPermissionCheckedListener != null) mPermissionCheckedListener.onPermissionGranted(true);
        }
    }

    /**
     * Request permissions access.
     * @param permissions the permission list
     */
    private void requestPermissions(String ... permissions) {
        requestPermissions(Arrays.asList(permissions));
    }

    /**
     * Request permissions access.
     * @param permissions the permission list
     */
    private void requestPermissions(List<String> permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> requiredPermissions = new ArrayList<>();

            int size = permissions.size();
            String permission;
            for (int i = 0; i < size; i++) {
                permission = permissions.get(i);
                if (!hasPermission(permission)) requiredPermissions.add(permission);
            }

            if (!requiredPermissions.isEmpty()) {
                mActivity.requestPermissions(
                        requiredPermissions.toArray(new String[requiredPermissions.size()]),
                        mRequestCode);
            }
        } else {
            // Treat the permission request as a success for API<23
            Log.i(getClass().getSimpleName(), "Requesting permission for API<23, treating it as a success");
            if (mPermissionCheckedListener != null) mPermissionCheckedListener.onPermissionGranted(true);
        }
    }

    /**
     * Display a permission information dialog
     */
    public Dialog initPermissionDetailDialog(final String permission, final String message) {
        return initPermissionDetailDialog(permission, message, -1);
    }

    /**
     * Display a permission information dialog
     */
    public Dialog initPermissionDetailDialog(final String permission, final String message, final int styleRes) {
        AlertDialog.Builder builder;
        if (styleRes != -1) builder = new AlertDialog.Builder(mActivity, styleRes);
        else builder = new AlertDialog.Builder(mActivity);

        builder.setMessage(message);

        // Positive button
        builder.setPositiveButton(mPositiveButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onPermissionDetailDialogPositiveClick(permission);
            }
        });

        // Negative button
        builder.setNegativeButton(mNegativeButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onPermissionDetailDialogNegativeClick(permission);
            }
        });

        // Don't allow information dialog to be cancellable
        builder.setCancelable(false);
        return builder.create();
    }

    /** Call this method if the positive button for detailed permission information dialog is selected */
    private void onPermissionDetailDialogPositiveClick(final String permission) {
        // Request for the permission immediately, don't show the message again
        requestPermission(permission);
    }

    /** Call this method if the negative button for detailed permission information dialog is selected */
    private void onPermissionDetailDialogNegativeClick(final String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Send an 'empty' result for Marshmallow and above
            mActivity.onRequestPermissionsResult(mRequestCode, new String[]{ permission }, new int[0]);
        }
    }

    /** The permission helper builder */
    public static class Builder {

        /** The {@link Activity} */
        private Activity mActivity;

        /** The {@link List} of permissions */
        private List<String> mPermissions;

        /** The {@link List} of permission information */
        private List<String> mPermissionInfos;

        /**
         * The request code. When unspecified,
         * the permission helper will generate a random number (1-100).
         */
        private int mRequestCode = -1;

        /** The positive button string for the permission info dialog */
        private String mPositiveButtonString;

        /** The negative button string for the permission info dialog */
        private String mNegativeButtonString;

        /** The listener for permission requests */
        private OnPermissionCheckedListener mPermissionCheckedListener;

        /** Default constructor */
        public Builder(Activity activity) {
            mActivity = activity;
        }

        /** Set the permissions */
        public final Builder withPermissions(String ... permissions) {
            mPermissions = Arrays.asList(permissions);
            return this;
        }

        /** Set the permissions */
        public final Builder withPermissions(List<String> permissions) {
            mPermissions = permissions;
            return this;
        }

        /** Set the permissions information */
        public final Builder withPermissionInfos(String ... permissionInfos) {
            mPermissionInfos = Arrays.asList(permissionInfos);
            return this;
        }

        /** Set the permission information */
        public final Builder withPermissionInfos(List<String> permissionInfos) {
            mPermissionInfos = permissionInfos;
            return this;
        }

        /** Set the request code */
        public final Builder withRequestCode(int requestCode) {
            mRequestCode = requestCode;
            return this;
        }

        /** Set the positive button string text */
        public final Builder withPositiveButtonString(int stringRes) {
            return withPositiveButtonString(mActivity.getString(stringRes));
        }

        /** Set the positive button string text */
        public final Builder withPositiveButtonString(String string) {
            mPositiveButtonString = string;
            return this;
        }

        /** Set the negative button string text */
        public final Builder withNegativeButtonString(int stringRes) {
            return withNegativeButtonString(mActivity.getString(stringRes));
        }

        /** Set the negative button string text */
        public final Builder withNegativeButtonString(String string) {
            mNegativeButtonString = string;
            return this;
        }

        /** Set the listener for permission requests */
        public final Builder withListener(OnPermissionCheckedListener listener) {
            mPermissionCheckedListener = listener;
            return this;
        }

        /** Obtain the activity */
        private Activity getActivity() {
            return mActivity;
        }

        /** Obtain the permission list */
        private List<String> getPermissions() {
            return mPermissions;
        }

        /** Obtain the permission information list */
        private List<String> getPermissionInfos() {
            return mPermissionInfos;
        }

        /** Obtain the request code */
        private int getRequestCode() {
            return mRequestCode;
        }

        /** Obtain the positive button string */
        private String getPositiveButtonString() {
            return mPositiveButtonString;
        }

        /** Obtain the negative button string */
        private String getNegativeButtonString() {
            return mNegativeButtonString;
        }

        /** Obtain the listener for permission requests */
        private OnPermissionCheckedListener getPermissionCheckedListener() {
            return mPermissionCheckedListener;
        }

        /** Build the permission helper */
        public final PermissionHelper build() {
            return new PermissionHelper(this);
        }

        /** Build the permission helper */
        public final void requestPermission() {
            new PermissionHelper(this).requestPermission();
        }
    }

    /** Listener to handle permission requests */
    public interface OnPermissionCheckedListener {

        /**
         * Called when all permission is granted.
         * @param isPermissionAlreadyGranted determines if the permission(s)
         *                                   were already granted before.
         */
        void onPermissionGranted(boolean isPermissionAlreadyGranted);

        /** Called when some permission is denied */
        void onPermissionDenied();
    }

}
