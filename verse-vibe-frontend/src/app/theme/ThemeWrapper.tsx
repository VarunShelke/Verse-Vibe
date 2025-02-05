'use client';

import { CssBaseline, ThemeProvider } from '@mui/material';
import theme from './theme';
import React from 'react';

export default function ThemeWrapper({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline>{children}</CssBaseline>
    </ThemeProvider>
  );
}
