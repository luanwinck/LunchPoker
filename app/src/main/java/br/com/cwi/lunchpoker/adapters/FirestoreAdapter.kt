package br.com.cwi.lunchpoker.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.firestore.*


abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(private var query: Query) : RecyclerView.Adapter<VH>(), EventListener<QuerySnapshot> {

    private var listSnapshots = ArrayList<DocumentSnapshot>()

    private var registration: ListenerRegistration? = null

    companion object {
        private const val TAG = "Firestore"
    }

    fun startListening() {
        if (query != null && registration == null) {
            registration = query!!.addSnapshotListener(this)
        }
    }

    fun stopListening() {
        if (registration != null){
            registration!!.remove()
            registration = null
        }

        listSnapshots.clear()
        notifyDataSetChanged()
    }

    override fun onEvent(queryDocumentSnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        if (exception != null) {
            Log.w(TAG, exception)
            return
        }

        for (change in queryDocumentSnapshot!!.documentChanges) {
            val snapshot = change.document

            when(change.type) {
                DocumentChange.Type.ADDED -> onDocumentAdded(change)
                DocumentChange.Type.MODIFIED -> onDocumentModified(change)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
            }
        }
    }

    override fun getItemCount() = listSnapshots.size

    fun getSnapshot(index: Int) = listSnapshots[index]

    fun onDocumentAdded(change: DocumentChange) {
        listSnapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            listSnapshots[change.oldIndex] = change.document
            notifyItemChanged(change.oldIndex)
        } else {
            listSnapshots.removeAt(change.oldIndex)
            listSnapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    fun onDocumentRemoved(change: DocumentChange) {
        listSnapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }


}



