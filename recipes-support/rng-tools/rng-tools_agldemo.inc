# Pick up modified default configuration for virtual machines
# that disables jitter entropy source, as it is not useful when
# the VMs are effectively driven by the host RNG via virtio-rng.
# This also helps boot speed, as otherwise the guests have rngd
# potentially somewhat pointlessly burning a lot of CPU on boot,
# in addition to the host's own rngd.
FILESEXTRAPATHS:prepend:virtio-all := "${THISDIR}/files:"
