import { Box, Button, TextField } from '@mui/material';
import { ChangeEventHandler, MouseEventHandler } from 'react';
import styles from './SearchBox.module.css';

interface SearchBoxProps {
  value: string;
  onChange: ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>;
  search: MouseEventHandler<HTMLButtonElement>;
  clearResults: MouseEventHandler<HTMLButtonElement>;
}

export default function SearchBox(props: SearchBoxProps) {
  return (
    <Box className={styles['search-box-container']}>
      <TextField
        className={styles['search-input']}
        placeholder="Search"
        value={props.value}
        onChange={(event) => props.onChange(event)}
      />
      <Button
        className={styles['search-button']}
        variant="contained"
        disabled={!props.value.trim()}
        onClick={(event) => props.search(event)}
      >
        Search
      </Button>
      <Button
        className={styles['search-button']}
        variant="outlined"
        onClick={(event) => props.clearResults(event)}
      >
        Clear Results
      </Button>
    </Box>
  );
}
