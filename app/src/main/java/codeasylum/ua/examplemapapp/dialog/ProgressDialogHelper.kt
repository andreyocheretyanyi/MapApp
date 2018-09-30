package codeasylum.ua.examplemapapp.dialog

import android.app.ProgressDialog
import android.content.Context
import codeasylum.ua.examplemapapp.R

object ProgressDialogHelper {

    private var mAlertDialog: ProgressDialog? = null

    fun show(context: Context?) {
        if (mAlertDialog == null && context != null) {
            mAlertDialog = ProgressDialog(context)
            mAlertDialog!!.setMessage(context.getString(R.string.loading))
            mAlertDialog!!.setCancelable(false)
            mAlertDialog!!.show()
        }
    }

    fun dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog!!.dismiss()
            mAlertDialog = null
        }
    }
}