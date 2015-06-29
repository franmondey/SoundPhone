/**
 * File generated by Magnet rest2mobile 1.1 - May 20, 2015 11:24:35 AM
 * @see {@link http://developer.magnet.com}
 */

package com.franmondey.soundphone.controller.api;

import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;

import com.franmondey.soundphone.model.beans.*;

public interface SoundApi {

  /**
   * Generated from URL GET https://api.soundcloud.com/e1/me/stream?limit=50&oauth_token=*************&format=json&_status_code_map%5B302%5D=200
   * GET e1/me/stream
   * @param limit  style:QUERY
   * @param oauth_token  style:QUERY
   * @param format  style:QUERY
   * @param _statusCodeMap5B3025D (original name : _status_code_map%5B302%5D) style:QUERY
   * @param listener
   * @return FetchSetsResult
   */
  Call<FetchSetsResult> fetchSets(
     String limit,
     String oauth_token,
     String format,
     String _statusCodeMap5B3025D,
     StateChangedListener listener
  );


}
