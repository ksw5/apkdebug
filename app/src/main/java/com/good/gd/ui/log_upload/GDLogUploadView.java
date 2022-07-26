package com.good.gd.ui.log_upload;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.log.GDLogManager;
import com.good.gd.log.GDLogUploadState;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDLogUploadView extends GDView {
    private static final int PROGRESS_BAR_THRESHOLD = 100;
    private final String LOG_STATUS_CANCELLED;
    private final String LOG_STATUS_COMPLETED;
    private final String LOG_STATUS_IN_PROGRESS;
    private final String LOG_STATUS_PREFIX;
    private final String LOG_STATUS_RESUMED;
    private final String LOG_STATUS_SUSPENDED;
    private final String NETWORK_STATUS_CELLULAR;
    private final String NETWORK_STATUS_PREFIX;
    private final String NETWORK_STATUS_UNREACHABLE;
    private final String NETWORK_STATUS_WIFI;
    private Button btnCancelUpload;
    private Button btnChangeUploadState;
    private final GDCustomizedUI customizedUI;
    private final LogUploadStateChangesReceiver logUploadStateChangesReceiver;
    private LogUploadNetworkState networkState;
    private TextView tvBytesProgress;
    private TextView tvLogUploaderStatus;
    private TextView tvNetworkStatus;
    private TextView tvPercentProgress;
    private BBDUIObject uiData;
    private ProgressBar uploadingProgress;
    private final String BUTTON_TEXT_CLOSE = GDLocalizer.getLocalizedString("Button Close");
    private final String BUTTON_TEXT_UPLOAD = GDLocalizer.getLocalizedString("Button Upload");
    private final String BUTTON_TEXT_CANCEL = GDLocalizer.getLocalizedString("Button Cancel");
    private final String BUTTON_TEXT_RESUME = GDLocalizer.getLocalizedString("Button Resume");
    private final String BUTTON_TEXT_SUSPEND = GDLocalizer.getLocalizedString("Button Suspend");
    private final String BUTTON_TEXT_COMPLETED = GDLocalizer.getLocalizedString("Completed");
    private final GDLogManager logManager = GDLogManager.getInstance();
    private GDLogUploadState currentState = GDLogUploadState.Idle;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class fdyxd extends GDViewDelegateAdapter implements LogUploadStateChangeListener {
        private fdyxd() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            GDLog.DBGPRINTF(16, "GDLogUploadViewDelegateAdapter.onActivityCreate\n");
            GDLogUploadState gDLogUploadState = bundle != null ? (GDLogUploadState) bundle.getSerializable("state_key") : null;
            if (gDLogUploadState != null) {
                GDLogUploadView.this.currentState = gDLogUploadState;
            } else {
                GDLogUploadView.this.setStateAtStart();
            }
            GDLogUploadView.this.logUploadStateChangesReceiver.addListener(this);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            GDLog.DBGPRINTF(16, "GDLogUploadViewDelegateAdapter.onActivityResume\n");
            GDLogUploadView.this.activityResumed();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStop() {
            super.onActivityStop();
            GDLog.DBGPRINTF(16, "GDLogUploadViewDelegateAdapter.onActivityStop: unregister receivers\n");
            GDLogUploadView.this.logUploadStateChangesReceiver.removeListener(this);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            bundle.putSerializable("state_key", GDLogUploadView.this.currentState);
        }

        @Override // com.good.gd.ui.log_upload.LogUploadStateChangeListener
        public void updateLogUploadState(GDLogUploadState gDLogUploadState) {
            GDLogUploadView.this.handleLogUploadStatus(gDLogUploadState);
        }

        @Override // com.good.gd.ui.log_upload.LogUploadStateChangeListener
        public void updateNetworkStatus(LogUploadNetworkState logUploadNetworkState) {
            GDLogUploadView.this.setNetworkState(logUploadNetworkState);
            GDLogUploadView.this.updateNetworkStatus();
        }

        /* synthetic */ fdyxd(GDLogUploadView gDLogUploadView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDLogUploadView.this.btnCancelUploadClicked();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDLogUploadView.this.btnChangeUploadStateClicked();
        }
    }

    public GDLogUploadView(Context context, ViewInteractor viewInteractor, BBDUIObject bBDUIObject, ViewCustomizer viewCustomizer, LogUploadStateChangesReceiver logUploadStateChangesReceiver, LogUploadNetworkState logUploadNetworkState) {
        super(context, viewInteractor, viewCustomizer);
        String str = GDLocalizer.getLocalizedString("Log uploader status") + ": ";
        this.LOG_STATUS_PREFIX = str;
        this.LOG_STATUS_RESUMED = str + GDLocalizer.getLocalizedString("Resumed");
        this.LOG_STATUS_CANCELLED = str + GDLocalizer.getLocalizedString("Cancelled");
        this.LOG_STATUS_SUSPENDED = str + GDLocalizer.getLocalizedString("Suspended");
        this.LOG_STATUS_COMPLETED = str + GDLocalizer.getLocalizedString("Completed");
        this.LOG_STATUS_IN_PROGRESS = str + GDLocalizer.getLocalizedString("In progress");
        String str2 = GDLocalizer.getLocalizedString("Connection Status") + ": ";
        this.NETWORK_STATUS_PREFIX = str2;
        this.NETWORK_STATUS_WIFI = str2 + GDLocalizer.getLocalizedString("Via Wi-Fi Status");
        this.NETWORK_STATUS_CELLULAR = str2 + GDLocalizer.getLocalizedString("Via Cellular Status");
        this.NETWORK_STATUS_UNREACHABLE = str2 + GDLocalizer.getLocalizedString("Unreachable Status");
        GDLog.DBGPRINTF(16, "GDLogUploadView.GDLogUploadView: creating GDLogUploadView\n");
        this.uiData = bBDUIObject;
        this.logUploadStateChangesReceiver = logUploadStateChangesReceiver;
        this.networkState = logUploadNetworkState;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        initLayout();
        initLifecycleHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activityResumed() {
        updateNetworkStatus();
        prepareProgressElements();
        checkViewState();
        handleLogUploadStatus(this.currentState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void btnCancelUploadClicked() {
        if (uploadingStarted()) {
            GDLog.DBGPRINTF(16, "GDLogUploadView.btnCancelUploadClicked: cancelling upload\n");
            this.logManager.cancelUpload();
            return;
        }
        GDLog.DBGPRINTF(16, "GDLogUploadView.btnCancelUploadClicked: closing GDLogUploadView\n");
        closeLogUploadView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void btnChangeUploadStateClicked() {
        switch (this.currentState.ordinal()) {
            case 0:
            case 3:
                startUploading();
                this.logManager.startUpload();
                return;
            case 1:
            case 5:
                suspendUploading();
                this.logManager.suspendUpload();
                return;
            case 2:
                closeLogUploadView();
                return;
            case 4:
                resumeUploading();
                this.logManager.resumeUpload();
                return;
            default:
                return;
        }
    }

    private void cancelUploading() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.cancelUploading:\n");
        prepareProgressElements();
        setUploadingStatus(this.LOG_STATUS_CANCELLED);
    }

    private void checkCancelButtonText() {
        switch (this.currentState.ordinal()) {
            case 0:
            case 2:
            case 3:
                this.btnCancelUpload.setText(this.BUTTON_TEXT_CLOSE);
                return;
            case 1:
            case 4:
            case 5:
                this.btnCancelUpload.setText(this.BUTTON_TEXT_CANCEL);
                return;
            default:
                return;
        }
    }

    private void checkChangeStateButtonText() {
        switch (this.currentState.ordinal()) {
            case 0:
            case 3:
                this.btnChangeUploadState.setText(this.BUTTON_TEXT_UPLOAD);
                return;
            case 1:
            case 5:
                this.btnChangeUploadState.setText(this.BUTTON_TEXT_SUSPEND);
                return;
            case 2:
                this.btnChangeUploadState.setText(this.BUTTON_TEXT_COMPLETED);
                return;
            case 4:
                this.btnChangeUploadState.setText(this.BUTTON_TEXT_RESUME);
                return;
            default:
                return;
        }
    }

    private void checkViewState() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.checkViewState: refreshing view state\n");
        checkChangeStateButtonText();
        checkCancelButtonText();
    }

    private void closeLogUploadView() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.closeLogUploadView: with state = " + this.currentState + '\n');
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
    }

    private void completeUploading() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.completeUploading:\n");
        setUploadingStatus(this.LOG_STATUS_COMPLETED);
    }

    private String getBytesProgress(long j, long j2) {
        return String.format("%s %s %s", Formatter.formatShortFileSize(getContext(), j), GDLocalizer.getLocalizedString("of"), Formatter.formatShortFileSize(getContext(), j2));
    }

    private int getPercentageProgress(long j, long j2) {
        return (int) ((((float) j) / ((float) j2)) * 100.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogUploadStatus(GDLogUploadState gDLogUploadState) {
        this.currentState = gDLogUploadState;
        GDLog.DBGPRINTF(16, "GDLogUploadView.handleLogUploadStatus: Current state: " + this.currentState + '\n');
        switch (this.currentState.ordinal()) {
            case 1:
                publishProgress();
                setUploadingStatus(this.LOG_STATUS_IN_PROGRESS);
                break;
            case 2:
                publishProgress();
                completeUploading();
                break;
            case 3:
                cancelUploading();
                break;
            case 4:
                publishProgress();
                suspendUploading();
                break;
            case 5:
                publishProgress();
                resumeUploading();
                break;
        }
        checkViewState();
    }

    private void initButtons() {
        Button button = (Button) findViewById(R.id.bbd_btn_cancel_upload_logs);
        this.btnCancelUpload = button;
        button.setText(this.BUTTON_TEXT_CANCEL);
        this.btnCancelUpload.setOnClickListener(new hbfhc());
        Button button2 = (Button) findViewById(R.id.bbd_btn_change_upload_logs_state);
        this.btnChangeUploadState = button2;
        button2.setText(this.BUTTON_TEXT_UPLOAD);
        this.btnChangeUploadState.setOnClickListener(new yfdke());
    }

    private void initLayout() {
        inflateLayout(R.layout.bbd_logs_upload_view, this);
        enableBottomLine();
        adjustHeaderPositioning();
        initButtons();
        initNetworkStatusView();
        initProgressElements();
        applyUICustomization();
    }

    private void initLifecycleHandler() {
        this._delegate = new fdyxd(this, null);
    }

    private void initNetworkStatusView() {
        this.tvNetworkStatus = (TextView) findViewById(R.id.bbd_tv_network_status);
    }

    private void initProgressElements() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.bbd_progress_uploading_logs);
        this.uploadingProgress = progressBar;
        progressBar.setIndeterminate(false);
        this.uploadingProgress.setMax(100);
        this.tvBytesProgress = (TextView) findViewById(R.id.bbd_tv_bytes_progress);
        this.tvPercentProgress = (TextView) findViewById(R.id.bbd_tv_percent_progress);
        TextView textView = (TextView) findViewById(R.id.bbd_tv_log_uploader_status);
        this.tvLogUploaderStatus = textView;
        textView.setText(this.LOG_STATUS_PREFIX);
    }

    private void prepareProgressElements() {
        this.tvBytesProgress.setText("");
        this.tvPercentProgress.setText("");
        this.uploadingProgress.setProgress(0);
        showProgressElements();
    }

    private void publishProgress() {
        long uploadBytesSent = this.logManager.getUploadBytesSent();
        long uploadBytesTotal = this.logManager.getUploadBytesTotal();
        GDLog.DBGPRINTF(16, "GDLogUploadView.publishProgress: bytesSent = " + uploadBytesSent + '\n');
        GDLog.DBGPRINTF(16, "GDLogUploadView.publishProgress: bytesTotal = " + uploadBytesTotal + '\n');
        int percentageProgress = getPercentageProgress(uploadBytesSent, uploadBytesTotal);
        this.uploadingProgress.setProgress(percentageProgress);
        this.tvPercentProgress.setText(percentageProgress + "%");
        this.tvBytesProgress.setText(getBytesProgress(uploadBytesSent, uploadBytesTotal));
    }

    private void resumeUploading() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.resumeUploading:\n");
        setUploadingStatus(this.LOG_STATUS_RESUMED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNetworkState(LogUploadNetworkState logUploadNetworkState) {
        this.networkState = logUploadNetworkState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStateAtStart() {
        GDLogUploadState uploadState = this.logManager.getUploadState();
        switch (uploadState.ordinal()) {
            case 0:
            case 2:
            case 3:
                this.currentState = GDLogUploadState.Idle;
                return;
            case 1:
            case 4:
            case 5:
                this.currentState = uploadState;
                return;
            default:
                return;
        }
    }

    private void setUploadingStatus(String str) {
        this.tvLogUploaderStatus.setText(str);
    }

    private void showProgressElements() {
        this.tvBytesProgress.setVisibility(0);
        this.tvPercentProgress.setVisibility(0);
    }

    private void startUploading() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.startUploading:\n");
        prepareProgressElements();
    }

    private void suspendUploading() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.suspendUploading:\n");
        this.btnChangeUploadState.setText(GDLocalizer.getLocalizedString(this.BUTTON_TEXT_RESUME));
        setUploadingStatus(this.LOG_STATUS_SUSPENDED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkStatus() {
        String str;
        LogUploadNetworkState logUploadNetworkState = this.networkState;
        if (logUploadNetworkState.connected && logUploadNetworkState.available) {
            int i = logUploadNetworkState.type;
            if (1 == i) {
                str = this.NETWORK_STATUS_WIFI;
            } else if (i == 0) {
                str = this.NETWORK_STATUS_CELLULAR;
            } else {
                str = logUploadNetworkState.typeName;
            }
        } else {
            str = this.NETWORK_STATUS_UNREACHABLE;
        }
        TextView textView = this.tvNetworkStatus;
        if (textView != null) {
            textView.setText(str);
        }
    }

    private boolean uploadingInProgress() {
        GDLogUploadState gDLogUploadState = this.currentState;
        return gDLogUploadState == GDLogUploadState.InProgress || gDLogUploadState == GDLogUploadState.Resumed;
    }

    private boolean uploadingStarted() {
        return uploadingInProgress() || this.currentState == GDLogUploadState.Suspended;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        this.btnCancelUpload.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.btnChangeUploadState.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        ((GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.uploadingProgress.getProgressDrawable()).findDrawableByLayerId(16908301)).getDrawable()).setColor(this.customizedUI.getCustomUIColor().intValue());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        GDLog.DBGPRINTF(16, "GDLogUploadView.onBackPressed: with state = " + this.currentState + '\n');
        closeLogUploadView();
    }
}
