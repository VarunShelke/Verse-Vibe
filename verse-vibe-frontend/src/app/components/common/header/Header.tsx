import { AppBar, Typography, Toolbar } from "@mui/material";
import styles from "./Header.module.css";
import { APP_NAME } from "@/app/constants/constants";

export default function Header() {
  return (
    <AppBar position="static" className={styles["app-bar"]}>
      <Toolbar className={styles.toolbar}>
        <Typography variant="h1" component="div" className={styles.title}>
          {APP_NAME}
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
