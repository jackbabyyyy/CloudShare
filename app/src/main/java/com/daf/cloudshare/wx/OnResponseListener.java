package com.daf.cloudshare.wx;

public interface OnResponseListener {

void onSuccess();
void onCancel();
void onFail(String message); }