package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;

class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    protected void onPreExecute() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0019 in list [B:19:0x0037]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r7 = this;
        r6 = 1;
        super.onPreExecute();
        r3 = "onPreExecute";
        r2 = r7.createAndStartTimingMetric(r3);
        r1 = 0;
        r3 = r7.kit;	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r1 = r3.onPreExecute();	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x0019;
    L_0x0016:
        r7.cancel(r6);
    L_0x0019:
        return;
    L_0x001a:
        r0 = move-exception;
        throw r0;	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
    L_0x001c:
        r3 = move-exception;
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x0025;
    L_0x0022:
        r7.cancel(r6);
    L_0x0025:
        throw r3;
    L_0x0026:
        r0 = move-exception;
        r3 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r4 = "Fabric";	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r5 = "Failure onPreExecute()";	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r3.e(r4, r5, r0);	 Catch:{ UnmetDependencyException -> 0x001a, Exception -> 0x0026, all -> 0x001c }
        r2.stopMeasuring();
        if (r1 != 0) goto L_0x0019;
    L_0x0037:
        r7.cancel(r6);
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.InitializationTask.onPreExecute():void");
    }

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    protected Result doInBackground(Void... voids) {
        TimingMetric timingMetric = createAndStartTimingMetric("doInBackground");
        Result result = null;
        if (!isCancelled()) {
            result = this.kit.doInBackground();
        }
        timingMetric.stopMeasuring();
        return result;
    }

    protected void onPostExecute(Result result) {
        this.kit.onPostExecute(result);
        this.kit.initializationCallback.success(result);
    }

    protected void onCancelled(Result result) {
        this.kit.onCancelled(result);
        this.kit.initializationCallback.failure(new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled"));
    }

    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric createAndStartTimingMetric(String event) {
        TimingMetric timingMetric = new TimingMetric(this.kit.getIdentifier() + "." + event, TIMING_METRIC_TAG);
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
