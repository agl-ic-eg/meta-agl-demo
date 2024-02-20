---
description: Feature agl-kvm-host-kuksa
authors: Scott Murray <scott.murray@konsulko.com>
---

### Feature agl-kvm-host-kuksa

* Enables support for running KUKSA.val databroker on host in KVM+QEMU demo images

### Dependent features pulled by agl-kvm-host-kuksa

The following features are pulled:

* agl-kvm

Note that enabling this feature results in a configuration where building images
other than agl-kvm-demo-platform will likely not give the desired results.
