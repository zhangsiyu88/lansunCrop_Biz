/*
 * Copyright (C) 2012 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lansun.qmyo.maijie_biz.qrcode.camera.open;

import android.hardware.Camera;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class OpenCameraInterface {

	private static final String TAG = OpenCameraInterface.class.getName();

	/**
	 * Opens the requested camera with {@link Camera#open(int)}, if one exists.
	 * 
	 * @param cameraId
	 *            camera ID of the camera to use. A negative value means
	 *            "no preference"
	 * @return handle to {@link Camera} that was opened
	 */
	public static Camera open(int cameraId) {

		int numCameras = Camera.getNumberOfCameras();
		if (numCameras == 0) {
			FrameLog.w(TAG, "No cameras!");
			return null;
		}

		boolean explicitRequest = cameraId >= 0;

		if (!explicitRequest) {
			// Select a camera if no explicit camera requested
			int index = 0;
			while (index < numCameras) {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				Camera.getCameraInfo(index, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
					break;
				}
				index++;
			}

			cameraId = index;
		}

		Camera camera;
		if (cameraId < numCameras) {
			FrameLog.i(TAG, "Opening camera #" + cameraId);
			camera = Camera.open(cameraId);//开启对应cameraId的 camera
		} else {
			if (explicitRequest) {
				FrameLog.w(TAG, "Requested camera does not exist: " + cameraId);
				camera = null;
			} else {
				FrameLog.i(TAG, "No camera facing back; returning camera #0");
				camera = Camera.open(0);//默认开启背部的 camera，cameraId为 0
			}
		}
		return camera;
	}

	/**
	 * Opens a rear-facing camera with {@link Camera#open(int)}, if one exists,
	 * or opens camera 0.
	 * 
	 * @return handle to {@link Camera} that was opened
	 */
	public static Camera open() {
		return open(-1);
	}

}
