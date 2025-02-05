import { Song } from '@/app/models/models';
import { Box, Typography } from '@mui/material';
import SongCard from './SongCard';
import styles from './SearchResults.module.css';

interface SearchResultsProps {
  results: Song[];
}

export default function SearchResults({ results }: SearchResultsProps) {
  return (
    <Box className={styles['search-results-container']}>
      {results.length > 0 ? (
        results.map((result) => <SongCard key={result.elasticSearchId} song={result} />)
      ) : (
        <Typography>No results found.</Typography>
      )}
    </Box>
  );
}
