DISTRO_FEATURES:append = " virtualization"

#
# Attempt to make guest machine derived from host machine, as opposed to
# hard-coding it in the multiconfig conf file.  Unfortunately, using any
# of the expanded overrides or e.g. TUNE_ARCH from the host MACHINE seems
# not feasible here with respect to BitBake conf file parse ordering, so
# a generic mapping to e.g. virtio-${TUNE_ARCH} is sadly not currently
# workable.
#

# Define a default, for now use one that will cause an immediate error.
AGL_GUEST_MACHINE = "INVALID"

# Set for demo target machine
AGL_GUEST_MACHINE:h3ulcb = "virtio-aarch64"

# Force variable value to be live before multiconfigs are parsed to make
# BitBake parser happy
AGL_GUEST_MACHINE := "${AGL_GUEST_MACHINE}"

BBMULTICONFIG = "agl-kvm-guest"

AGL_FEATURES:append = " agl-kvm"

# Override remote display network configuration to use KVM demo's
# network configuration
REMOTING_OUTPUT_HOST = "172.16.10.3"

# Override cluster dashboard VIS server host for KVM demo's network
# configuration
CLUSTER_DEMO_VSS_HOSTNAME = "172.16.10.1"

include mc-conf-${BB_CURRENT_MC}.inc
