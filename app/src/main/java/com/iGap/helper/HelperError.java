/*
* This is the source code of iGap for Android
* It is licensed under GNU AGPL v3.0
* You should have received a copy of the license in this archive (see LICENSE).
* Copyright © 2017 , iGap - www.iGap.net
* iGap Messenger | Free, Fast and Secure instant messaging application
* The idea of the RooyeKhat Media Company - www.RooyeKhat.co
* All rights reserved.
*/

package com.iGap.helper;

import android.support.design.widget.Snackbar;
import android.view.View;
import com.iGap.G;
import com.iGap.R;
import com.iGap.module.LoginActions;


public class HelperError {

    public static String getErrorFromCode(int majorCode, int minorCode) {

        String error = "";
        switch (majorCode) {
            case 2:
                if (minorCode == 1) {
                    G.userLogin = false;
                    error = G.context.getResources().getString(R.string.E_2);
                    LoginActions.login();
                }
                break;
            case 5:
                //if (minorCode == 1) error = "time out  server not response";
                break;
            case 9:
                /*if (G.currentActivity != null) {
                    G.currentActivity.finish();
                }
                Intent intent = new Intent(G.context, ActivityProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                G.context.startActivity(intent);*/
                break;
            case 109:
                error = G.context.getResources().getString(R.string.E_109);
                HelperLogout.logout();
                break;
            case 110:
                error = G.context.getResources().getString(R.string.E_110);
                break;
            case 111:
                if (minorCode != 4) {
                    HelperLogout.logout();
                } else {
                    error = G.context.getResources().getString(R.string.E_111);
                }
                break;
            case 112:
                error = G.context.getResources().getString(R.string.E_112);
                break;
            case 113:
                error = G.context.getResources().getString(R.string.E_113);
                break;
            case 114:
                error = G.context.getResources().getString(R.string.E_114);
                break;
            case 115:
                error = G.context.getResources().getString(R.string.E_115);
                break;
            case 116:
                error = G.context.getResources().getString(R.string.E_116);
                break;
            case 122:
                error = G.context.getResources().getString(R.string.E_122);
                break;
            case 123:
                error = G.context.getResources().getString(R.string.E_123);
                break;
            case 124:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_124_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_124_2);
                } else if (minorCode == 3) {
                    error = G.context.getResources().getString(R.string.E_124_3);
                } else if (minorCode == 4) {
                    error = G.context.getResources().getString(R.string.E_124_4);
                }
                break;
            case 125:
                error = G.context.getResources().getString(R.string.E_125);
                break;
            case 154:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_154_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_154_2);
                }
                break;

            case 155:
                error = G.context.getResources().getString(R.string.E_155);
                break;
            case 156:
                error = G.context.getResources().getString(R.string.E_156);
                break;
            case 157:
                error = G.context.getResources().getString(R.string.E_157);
                break;
            case 158:
                error = G.context.getResources().getString(R.string.E_158);
                break;

            case 200:
                error = G.context.getResources().getString(R.string.E_200);
                break;
            case 201:
                error = G.context.getResources().getString(R.string.E_201);
                break;
            case 202:
                error = G.context.getResources().getString(R.string.E_202);
                break;

            case 209:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_209_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_209_2);
                } else if (minorCode == 3) {
                    error = G.context.getResources().getString(R.string.E_209_3);
                }
                break;
            case 210:
                error = G.context.getResources().getString(R.string.E_210);
                break;
            case 211:
                error = G.context.getResources().getString(R.string.E_211);
                break;
            case 212:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_212_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_212_1);
                }
                break;

            case 213:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_213);
                break;
            case 214:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_214);
                break;
            case 218:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_218);
                break;
            case 219:
                error = G.context.getResources().getString(R.string.E_219);
                break;
            case 220:
                error = G.context.getResources().getString(R.string.E_220);
                break;
            case 233:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_233_1);
                }

                break;
            case 300:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_300_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_300_2);
                }
                break;
            case 301:
                error = G.context.getResources().getString(R.string.E_301);
                break;

            case 302:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_302_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_302_2);
                } else if (minorCode == 3) {
                    error = G.context.getResources().getString(R.string.E_302_3);
                } else if (minorCode == 4) {
                    error = G.context.getResources().getString(R.string.E_302_4);
                }
                break;
            case 303:
                error = G.context.getResources().getString(R.string.E_303);
                break;
            case 304:
                error = G.context.getResources().getString(R.string.E_304);
                break;
            case 305:
                error = G.context.getResources().getString(R.string.E_305);
                break;
            case 318:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_318_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_318_2);
                }
                break;
            case 319:
                error = G.context.getResources().getString(R.string.E_319);
                break;
            case 320:
                error = G.context.getResources().getString(R.string.E_320);
                break;
            case 321:
                error = G.context.getResources().getString(R.string.E_321);
                break;
            case 322:
                error = G.context.getResources().getString(R.string.E_322);
                break;
            case 323:
                error = G.context.getResources().getString(R.string.E_323);
                break;
            case 324:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_324_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_324_2);
                }
                break;
            case 325:
                error = G.context.getResources().getString(R.string.E_325);
                break;
            case 326:
                error = G.context.getResources().getString(R.string.E_326);
                break;

            case 327:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_327_A);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_327_B);
                }
                break;
            case 328:
                error = G.context.getResources().getString(R.string.E_328);
                break;
            case 329:
                error = G.context.getResources().getString(R.string.E_329);
                break;

            case 330:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_330_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_330_2);
                } else if (minorCode == 3) {
                    error = G.context.getResources().getString(R.string.E_330_3);
                }
                break;
            case 331:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_331);
                break;
            case 332:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_332_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_332_2);
                }
                break;

            case 333:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_333);
                break;
            case 334:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_334);
                break;
            case 335:
                error = G.context.getResources().getString(R.string.E_335);
                break;
            case 336:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_336);
                break;
            case 337:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_337);
                break;

            case 500:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.Toast_Location_Not_Found);
                break;
            case 502:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.Toast_Location_Not_Found);
                break;
            case 503:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.Toast_Location_Not_Found);
                break;

            case 610:
                error = G.context.getResources().getString(R.string.E_610);
                break;
            case 611:
                error = G.context.getResources().getString(R.string.E_611);
                break;
            case 612:
                error = G.context.getResources().getString(R.string.E_612);
                break;
            case 613:
                error = G.context.getResources().getString(R.string.E_613);
                break;
            case 614:
                //error = G.context.getResources().getString(R.string.E_614);
                break;
            case 615:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_615_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_615_2);
                }
                break;

            case 616:
                error = G.context.getResources().getString(R.string.E_616);
                break;
            case 617:
                error = "";// G.context.getResources().getString(R.string.E_617);
                break;
            case 620:
                error = "";// G.context.getResources().getString(R.string.there_is_no_sheared_media);
                break;
            case 623:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_713_1);
                break;
            case 629:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_713_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_713_1);
                } else if (minorCode == 3) error = G.context.getResources().getString(R.string.E_713_1);
                break;
            case 638:
                if (minorCode == 1) error = G.context.getResources().getString(R.string.E_713_1);
                break;
            case 713:
                if (minorCode == 1) {
                    error = G.context.getResources().getString(R.string.E_713_1);
                } else if (minorCode == 2) {
                    error = G.context.getResources().getString(R.string.E_713_2);
                } else if (minorCode == 3) {
                    error = G.context.getResources().getString(R.string.E_713_3);
                } else if (minorCode == 4) {
                    error = G.context.getResources().getString(R.string.E_713_4);
                } else if (minorCode == 5) {
                    error = G.context.getResources().getString(R.string.E_713_5);
                }
                break;
            case 714:
                error = G.context.getResources().getString(R.string.E_714);
                break;
            case 715:
                error = G.context.getResources().getString(R.string.E_715);
                break;
            case 99999: // client errors
                error = "Offset is negative";
            case -1: // client errors
                error = G.context.getResources().getString(R.string.please_try_again);
                break;

        }

        return error;
    }

    /**
     * use this method for detect text internal client error
     */
    public static String getClientErrorCode(int majorCode, int minorCode) {
        String error = "";
        switch (majorCode) {
            case -2:
                error = G.context.getResources().getString(R.string.room_not_exist);
                break;
        }
        return error;
    }


    public static void showSnackMessage(final String message) {

        if (message.length() > 0) {

            G.currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Snackbar snack = Snackbar.make(G.currentActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                    snack.setAction(R.string.cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snack.dismiss();
                        }
                    });
                    snack.show();
                }
            });
        }
    }
}