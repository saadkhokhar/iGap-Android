/*
* This is the source code of iGap for Android
* It is licensed under GNU AGPL v3.0
* You should have received a copy of the license in this archive (see LICENSE).
* Copyright © 2017 , iGap - www.iGap.net
* iGap Messenger | Free, Fast and Secure instant messaging application
* The idea of the RooyeKhat Media Company - www.RooyeKhat.co
* All rights reserved.
*/

package com.iGap.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.iGap.G;
import com.iGap.R;
import com.iGap.helper.HelperString;
import com.iGap.interfaces.OnChannelCheckUsername;
import com.iGap.interfaces.OnChannelUpdateUsername;
import com.iGap.interfaces.OnClientGetRoomResponse;
import com.iGap.proto.ProtoChannelCheckUsername;
import com.iGap.proto.ProtoClientGetRoom;
import com.iGap.proto.ProtoGlobal;
import com.iGap.realm.RealmRoom;
import com.iGap.realm.RealmRoomFields;
import com.iGap.request.RequestChannelCheckUsername;
import com.iGap.request.RequestChannelUpdateUsername;
import com.iGap.request.RequestClientGetRoom;
import io.realm.Realm;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.iGap.R.id.fragmentContainer;

public class FragmentCreateChannel extends Fragment implements OnChannelCheckUsername {

    private Long roomId;
    private String inviteLink;
    public static final int PRIVATE = 0;
    public static final int PUBLIC = 1;
    private EditText edtLink;
    private RadioButton raPublic;
    private RadioButton raPrivate;
    private TextInputLayout txtInputLayout;
    private TextView txtFinish;
    private String token;
    private boolean existAvatar;
    private ProgressBar prgWaiting;
    private String pathSaveImage;
    private Activity mActivity;

    public FragmentCreateChannel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_channel, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        G.onChannelCheckUsername = this;

        if (getArguments() != null) {
            roomId = getArguments().getLong("ROOMID");
            inviteLink = getArguments().getString("INVITE_LINK");
            token = getArguments().getString("TOKEN");


        }

        view.findViewById(R.id.fch_backgroundToolbar).setBackgroundColor(Color.parseColor(G.appBarColor));
        view.findViewById(R.id.view).setBackgroundColor(Color.parseColor(G.appBarColor));

        prgWaiting = (ProgressBar) view.findViewById(R.id.fch_prgWaiting_addContact);
        prgWaiting.setVisibility(View.GONE);

        ViewGroup vgRoot = (ViewGroup) view.findViewById(R.id.fch_root);
        vgRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView txtBack = (TextView) view.findViewById(R.id.fch_txt_back);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(FragmentCreateChannel.this).commit();
            }
        });

        TextView txtCancel = (TextView) view.findViewById(R.id.fch_txt_cancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(FragmentCreateChannel.this).commit();
            }
        });

        txtFinish = (TextView) view.findViewById(R.id.fch_txt_finish);
        txtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                G.onChannelUpdateUsername = new OnChannelUpdateUsername() {
                    @Override
                    public void onChannelUpdateUsername(final long roomId, final String username) {

                        if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Realm realm = Realm.getDefaultInstance();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmRoom realmRoom = realm.where(RealmRoom.class).equalTo(RealmRoomFields.ID, roomId).findFirst();
                                        realmRoom.getChannelRoom().setUsername(username);
                                        realmRoom.getChannelRoom().setPrivate(false);
                                    }
                                });
                                realm.close();
                                getRoom(roomId, ProtoGlobal.Room.Type.CHANNEL);
                            }
                        });


                    }

                    @Override
                    public void onError(int majorCode, int minorCode, int time) {
                        hideProgressBar();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.normal_error), Snackbar.LENGTH_LONG);

                                snack.setAction(getString(R.string.cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        snack.dismiss();
                                    }
                                });
                                snack.show();
                            }
                        });
                    }

                    @Override
                    public void onTimeOut() {
                        hideProgressBar();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.time_out), Snackbar.LENGTH_LONG);

                                snack.setAction(getString(R.string.cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        snack.dismiss();
                                    }
                                });
                                snack.show();
                            }
                        });
                    }
                };

                if ((raPrivate.isChecked() || edtLink.getText().toString().length() > 0) && roomId > 0) {

                    showProgressBar();

                    if (raPrivate.isChecked()) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmRoom realmRoom = realm.where(RealmRoom.class).equalTo(RealmRoomFields.ID, roomId).findFirst();
                                realmRoom.getChannelRoom().setPrivate(true);

                            }
                        });
                        realm.close();
                        getRoom(roomId, ProtoGlobal.Room.Type.CHANNEL);
                    } else {

                        String userName = edtLink.getText().toString().replace("iGap.net/", "");
                        new RequestChannelUpdateUsername().channelUpdateUsername(roomId, userName);

                    }
                }
            }
        });

        txtInputLayout = (TextInputLayout) view.findViewById(R.id.fch_txtInput_nikeName);
        txtInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (raPrivate.isChecked()) {
                    final PopupMenu popup = new PopupMenu(getActivity(), view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.menu_item_copy, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_link_copy:
                                    String copy;
                                    copy = edtLink.getText().toString();
                                    ClipboardManager clipboard = (ClipboardManager) G.context.getSystemService(CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("LINK_GROUP", copy);
                                    clipboard.setPrimaryClip(clip);

                                    break;
                            }
                            return true;
                        }
                    });

                    popup.show(); //
                }
            }
        });
        edtLink = (EditText) view.findViewById(R.id.fch_edt_link);
        edtLink.setText("iGap.net/");
        Selection.setSelection(edtLink.getText(), edtLink.getText().length());
        edtLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!raPrivate.isChecked()) {

                    if (!editable.toString().contains("iGap.net/")) {
                        edtLink.setText("iGap.net/");
                        Selection.setSelection(edtLink.getText(), edtLink.getText().length());

                    }
                    if (HelperString.regexCheckUsername(editable.toString().replace("iGap.net/", ""))) {
                        String userName = edtLink.getText().toString().replace("iGap.net/", "");
                        new RequestChannelCheckUsername().channelCheckUsername(roomId, userName);
                    } else {
                        txtFinish.setEnabled(false);
                        txtFinish.setTextColor(getResources().getColor(R.color.gray_6c));
                        txtInputLayout.setErrorEnabled(true);
                        txtInputLayout.setError("" + getResources().getString(R.string.INVALID));
                    }

                }
            }
        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.fch_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setInviteLink();
            }
        });


        raPublic = (RadioButton) view.findViewById(R.id.fch_radioButton_Public);
        raPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        raPrivate = (RadioButton) view.findViewById(R.id.fch_radioButton_private);
        raPrivate.setChecked(true);
        raPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setInviteLink();
    }

    private void getRoom(final Long roomId, final ProtoGlobal.Room.Type type) {
        G.onClientGetRoomResponse = new OnClientGetRoomResponse() {
            @Override public void onClientGetRoomResponse(final ProtoGlobal.Room room, ProtoClientGetRoom.ClientGetRoomResponse.Builder builder, String identity) {

                if (!identity.equals(RequestClientGetRoom.CreateRoomMode.requestFromOwner.toString())) return;


                try {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                hideProgressBar();
                                Fragment fragment = ContactGroupFragment.newInstance();
                                Bundle bundle = new Bundle();
                                bundle.putLong("RoomId", roomId);
                                bundle.putString("LIMIT", room.getGroupRoomExtra().getParticipantsCountLimitLabel());
                                bundle.putString("TYPE", type.toString());
                                bundle.putBoolean("NewRoom", true);
                                fragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)

                                        .replace(fragmentContainer, fragment, "contactGroup_fragment").commitAllowingStateLoss();
                                getActivity().getSupportFragmentManager().beginTransaction().remove(FragmentCreateChannel.this).commit();
                                //ActivityMain.mLeftDrawerLayout.closeDrawer();
                            }
                        });
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int majorCode, int minorCode) {
                hideProgressBar();
            }

            @Override
            public void onTimeOut() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                    }
                });

            }
        };

        new RequestClientGetRoom().clientGetRoom(roomId, RequestClientGetRoom.CreateRoomMode.requestFromOwner);
    }

    private void setInviteLink() {

        if (raPrivate.isChecked()) {
            edtLink.setText(inviteLink);
            edtLink.setEnabled(false);
            txtFinish.setEnabled(true);
            txtFinish.setTextColor(getResources().getColor(R.color.toolbar_background));
            txtInputLayout.setErrorEnabled(true);
            txtInputLayout.setError("");

        } else if (raPublic.isChecked()) {
            edtLink.setEnabled(true);
            edtLink.setText("");
        }
    }

    @Override
    public void onChannelCheckUsername(final ProtoChannelCheckUsername.ChannelCheckUsernameResponse.Status status) {

        if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (status == ProtoChannelCheckUsername.ChannelCheckUsernameResponse.Status.AVAILABLE) {

                    txtFinish.setEnabled(true);
                    txtFinish.setTextColor(getResources().getColor(R.color.toolbar_background));
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError("");
                } else if (status == ProtoChannelCheckUsername.ChannelCheckUsernameResponse.Status.INVALID) {
                    txtFinish.setEnabled(false);
                    txtFinish.setTextColor(getResources().getColor(R.color.gray_6c));
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError("" + getResources().getString(R.string.INVALID));

                } else if (status == ProtoChannelCheckUsername.ChannelCheckUsernameResponse.Status.TAKEN) {
                    txtFinish.setEnabled(false);
                    txtFinish.setTextColor(getResources().getColor(R.color.gray_6c));
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError("" + getResources().getString(R.string.TAKEN));
                } else if (status == ProtoChannelCheckUsername.ChannelCheckUsernameResponse.Status.OCCUPYING_LIMIT_EXCEEDED) {
                    txtFinish.setEnabled(false);
                    txtFinish.setTextColor(getResources().getColor(R.color.gray_6c));
                    txtInputLayout.setErrorEnabled(true);
                    txtInputLayout.setError("" + getResources().getString(R.string.OCCUPYING_LIMIT_EXCEEDED));
                }
            }
        });

    }

    @Override
    public void onError(int majorCode, int minorCode) {

        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override public void run() {
                    final Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.normal_error), Snackbar.LENGTH_LONG);
                    snack.setAction(R.string.cancel, new View.OnClickListener() {
                        @Override public void onClick(View view) {
                            snack.dismiss();
                        }
                    });
                    snack.show();
                }
            });
        }
    }

    @Override
    public void onTimeOut() {

        if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.time_out), Snackbar.LENGTH_LONG);

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

    private void showProgressBar() {

        if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                prgWaiting.setVisibility(View.VISIBLE);
                mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        });
    }

    private void hideProgressBar() {

        if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                prgWaiting.setVisibility(View.GONE);
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        });
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override public void onDetach() {

        if (prgWaiting.getVisibility() == View.VISIBLE) {
            hideProgressBar();
        }
        super.onDetach();
    }
}
