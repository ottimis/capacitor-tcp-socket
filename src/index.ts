import { registerPlugin } from '@capacitor/core';

import type { SocketTCPPlugin } from './definitions';

const SocketTCP = registerPlugin<SocketTCPPlugin>('SocketTCP', {
    ios: () => import('./web').then(m => new m.SocketTCPWeb()),
});

export * from './definitions';
export { SocketTCP };