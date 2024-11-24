DISTRO_FEATURES:append = " virtualization"

#
# Unfortunately, using any of the expanded overrides or e.g. TUNE_ARCH
# from the host MACHINE here seems not feasible due to BitBake conf
# file parse ordering, so a generic mapping to e.g. virtio-${TUNE_ARCH}
# is sadly not currently workable.
#

# Set default guest machine
AGL_GUEST_MACHINE = "virtio-aarch64"

# Hook to allow overriding AGL_GUEST_MACHINE for specific targets
include agl-kvm-${MACHINE}.inc

BBMULTICONFIG = "agl-kvm-guest"

AGL_FEATURES:append = " agl-kvm"

# Override remote display network configuration to use KVM demo's
# network configuration
REMOTING_OUTPUT_HOST = "172.16.10.3"

# Override cluster dashboard VIS server host for KVM demo's network
# configuration
CLUSTER_DEMO_VSS_HOSTNAME = "172.16.10.1"

include mc-conf-${BB_CURRENT_MC}.inc
