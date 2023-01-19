# We don't want the app framework bits (specifically the user session) in
# the KVM host image.
AGL_FEATURES:remove = "agl-app-fw"
