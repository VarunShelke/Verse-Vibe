import { APP_NAME } from '@/app/constants/constants';
import { Box, Typography } from '@mui/material';
import styles from './Footer.module.css';

export default function Footer() {
  const copyright: string = '\u00A9';
  const year: number = new Date().getFullYear();

  return (
    <Box component="footer" className={styles.footer}>
      <Typography variant="body2">{`${APP_NAME} ${copyright} ${year}`}</Typography>
    </Box>
  );
}
